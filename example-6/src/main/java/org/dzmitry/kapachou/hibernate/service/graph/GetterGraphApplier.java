package org.dzmitry.kapachou.hibernate.service.graph;

import org.hibernate.Hibernate;

import javax.persistence.Persistence;
import javax.persistence.PersistenceUtil;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.*;

public class GetterGraphApplier {

    private static final PersistenceUtil persistenceUtil = Persistence.getPersistenceUtil();

    @FunctionalInterface
    public interface Converter {
        Object convert(Object value, Class<?> targetType); // default: identity
    }

    public static final Converter IDENTITY = (v, t) -> (t == null || v == null || t.isInstance(v)) ? v : v;

    private GetterGraphApplier() {
    }

    public static void apply(Object source, Object target, GetterGraph<?> graph) {
        apply(source, target, graph, IDENTITY);
    }

    public static void apply(Object source, Object target, GetterGraph<?> graph, Converter converter) {
        if (source == null || target == null || graph == null) return;
        IdentityHashMap<Object, Boolean> seen = new IdentityHashMap<>();
        for (GetterGraph.Node n : graph.roots.values()) {
            applyNode(source, target, n, converter, seen);
        }
    }

    private static boolean isLoaded(Object owner, String attr) {
        try {
            return persistenceUtil.isLoaded(owner, attr);
        } catch (Exception ignored) {
            // Fallback: if Hibernate present, use isInitialized on the property value
            return false; // be permissive if provider doesn’t support attribute-level check
        }
    }

    private static void applyNode(Object srcOwner, Object tgtOwner, GetterGraph.Node node,
                                  Converter conv, IdentityHashMap<Object, Boolean> seen) {
        if (!isLoaded(srcOwner, node.name)) {
            return;
        }

        Object srcVal = invoke(srcOwner, node.getter);
        if (srcVal != null) initialize(srcVal);

        switch (node.kind) {
            case SINGULAR -> {
                set(tgtOwner, node.name, conv.convert(srcVal, setterType(tgtOwner, node.name)));
                if (!node.children.isEmpty() && srcVal != null) {
                    Object tgtChild = get(tgtOwner, node.name);
                    if (tgtChild == null) {
                        tgtChild = newInstanceOrNull(setterType(tgtOwner, node.name));
                        if (tgtChild != null) set(tgtOwner, node.name, tgtChild);
                    }
                    if (tgtChild != null && seen.putIfAbsent(srcVal, Boolean.TRUE) == null) {
                        for (var child : node.children.values()) {
                            applyNode(srcVal, tgtChild, child, conv, seen);
                        }
                    }
                }
            }
            case COLLECTION -> {
                Collection<?> srcCol = (srcVal instanceof Collection) ? (Collection<?>) srcVal : List.of();
                for (Object e : srcCol) if (e != null && !node.children.isEmpty()) initialize(e);
                Collection<Object> tgtCol = ensureCollection(tgtOwner, node.name);
                tgtCol.clear();
                for (Object e : srcCol) {
                    Object converted = conv.convert(e, Object.class); // plug deeper mapping if needed
                    tgtCol.add(converted);
                }
            }
            case MAP -> {
                Map<?, ?> srcMap = (srcVal instanceof Map) ? (Map<?, ?>) srcVal : Map.of();
                if (!node.keyChildren.isEmpty()) {
                    for (Object k : srcMap.keySet()) if (k != null) initialize(k);
                }
                if (!node.children.isEmpty()) {
                    for (Object v : srcMap.values()) if (v != null) initialize(v);
                }
                Map<Object, Object> tgtMap = ensureMap(tgtOwner, node.name);
                tgtMap.clear();
                for (var e : srcMap.entrySet()) {
                    Object k = conv.convert(e.getKey(), Object.class);
                    Object v = conv.convert(e.getValue(), Object.class);
                    tgtMap.put(k, v);
                }
            }
        }
    }

    /* ------------- reflection helpers ------------- */

    private static void initialize(Object o) {
        try {
            if (!Hibernate.isInitialized(o)) Hibernate.initialize(o);
        } catch (Throwable ignored) {
        }
    }

    private static Object invoke(Object owner, Method getter) {
        try {
            return getter.invoke(owner);
        } catch (Exception e) {
            return null;
        }
    }

    private static Object get(Object bean, String name) {
        String cap = Character.toUpperCase(name.charAt(0)) + name.substring(1);
        try {
            var m = bean.getClass().getMethod("get" + cap);
            m.setAccessible(true);
            return m.invoke(bean);
        } catch (NoSuchMethodException e) {
            try {
                var m = bean.getClass().getMethod("is" + cap);
                m.setAccessible(true);
                return m.invoke(bean);
            } catch (Exception ex) {
                return null;
            }
        } catch (Exception e) {
            return null;
        }
    }

    private static void set(Object bean, String name, Object value) {
        String cap = Character.toUpperCase(name.charAt(0)) + name.substring(1);
        for (Method m : bean.getClass().getMethods()) {
            if (!m.getName().equals("set" + cap) || m.getParameterCount() != 1) continue;
            Class<?> p = m.getParameterTypes()[0];
            if (value == null || p.isInstance(value) || isBoxingAssignable(p, value.getClass())) {
                try {
                    m.setAccessible(true);
                    m.invoke(bean, value);
                    return;
                } catch (Exception ignored) {
                }
            }
        }
    }

    private static Class<?> setterType(Object bean, String name) {
        String cap = Character.toUpperCase(name.charAt(0)) + name.substring(1);
        for (Method m : bean.getClass().getMethods()) {
            if (m.getName().equals("set" + cap) && m.getParameterCount() == 1) return m.getParameterTypes()[0];
        }
        return null;
    }

    @SuppressWarnings("unchecked")
    private static Collection<Object> ensureCollection(Object bean, String attr) {
        Object cur = get(bean, attr);
        if (cur instanceof Collection<?>) {
            return (Collection<Object>) cur;
        }
        var list = new ArrayList<>();
        set(bean, attr, list);
        return list;
    }

    @SuppressWarnings("unchecked")
    private static Map<Object, Object> ensureMap(Object bean, String attr) {
        Object cur = get(bean, attr);
        if (cur instanceof Map<?, ?>) {
            return (Map<Object, Object>) cur;

        }
        var map = new LinkedHashMap<>();
        set(bean, attr, map);
        return map;
    }

    private static boolean isBoxingAssignable(Class<?> to, Class<?> from) {
        if (!to.isPrimitive()) return false;
        return (to == boolean.class && from == Boolean.class)
                || (to == int.class && from == Integer.class)
                || (to == long.class && from == Long.class)
                || (to == double.class && from == Double.class)
                || (to == float.class && from == Float.class)
                || (to == short.class && from == Short.class)
                || (to == byte.class && from == Byte.class)
                || (to == char.class && from == Character.class);
    }

    private static Object newInstanceOrNull(Class<?> type) {
        if (type == null || type.isInterface() || type.isPrimitive()) return null;
        try {
            Constructor<?> c = type.getDeclaredConstructor();
            c.setAccessible(true);
            return c.newInstance();
        } catch (Exception e) {
            return null;
        }
    }

}
