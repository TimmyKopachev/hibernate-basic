package org.dzmitry.kapachou.hibernate.service.graph;

import javax.persistence.EntityGraph;
import javax.persistence.EntityManager;
import javax.persistence.Subgraph;
import javax.persistence.metamodel.*;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

public class EntityGraphBuilder<T> {
    private final EntityManager em;
    private final Class<T> rootType;
    private final EntityGraph<T> jpaGraph;

    private final Map<String, Subgraph<?>> rootSubgraphCache = new HashMap<>();
    private final Map<String, Subgraph<?>> rootKeySubgraphCache = new HashMap<>();

    private final GetterGraph<T> access;

    public EntityGraphBuilder(EntityManager em, Class<T> rootType) {
        this.rootType = rootType;
        this.em = em;
        this.jpaGraph = em.createEntityGraph(rootType);
        this.access = new GetterGraph<>(rootType);
    }

    /* ---------------- Root fetches ---------------- */

    @SuppressWarnings({"rawtypes"})
    private void addRootBasicAttributesToGetterGraph() {
        ManagedType<?> mt = em.getMetamodel().managedType(rootType);
        for (Attribute attr : mt.getAttributes()) {
            if (!(attr instanceof SingularAttribute)) continue;

            Attribute.PersistentAttributeType pat = attr.getPersistentAttributeType();
            boolean isBasicOrEmb = pat == Attribute.PersistentAttributeType.BASIC
                    || pat == Attribute.PersistentAttributeType.EMBEDDED;
            boolean isIdOrVersion = ((SingularAttribute) attr).isId() ||
                    ((SingularAttribute) attr).isVersion();
            if (!isBasicOrEmb && !isIdOrVersion) continue;

            String name = attr.getName();
            access.roots.computeIfAbsent(name, n ->
                    new GetterGraph.Node(
                            n,
                            GetterGraph.Kind.SINGULAR,
                            findGetter(rootType, n),
                            ((SingularAttribute) attr).getBindableJavaType(),
                            null
                    )
            );
        }
    }

    @SuppressWarnings({"rawtypes", "unchecked"})
    private void populateBasicsFor(Class<?> ownerType, GetterGraph.Node parent) {
        try {
            ManagedType<?> mt = em.getMetamodel().managedType(ownerType);
            for (Attribute attr : mt.getAttributes()) {
                if (!(attr instanceof SingularAttribute)) continue;

                Attribute.PersistentAttributeType pat = attr.getPersistentAttributeType();
                boolean isBasicOrEmb = pat == Attribute.PersistentAttributeType.BASIC
                        || pat == Attribute.PersistentAttributeType.EMBEDDED;
                boolean isIdOrVersion = ((SingularAttribute) attr).isId() || ((SingularAttribute) attr).isVersion();
                if (!isBasicOrEmb && !isIdOrVersion) continue;

                String name = attr.getName();
                parent.children.computeIfAbsent(name, n ->
                        new GetterGraph.Node(
                                n,
                                GetterGraph.Kind.SINGULAR,
                                findGetter(ownerType, n),
                                ((SingularAttribute) attr).getBindableJavaType(),
                                null
                        )
                );
            }
        } catch (IllegalArgumentException ignored) {
            // ownerType not a managed/embeddable type — nothing to add
        }
    }

    public <A> EntityGraphBuilder<T> fetch(SingularAttribute<? super T, A> attr) {
        jpaGraph.addAttributeNodes(attr.getName());
        access.roots.computeIfAbsent(attr.getName(), n -> new GetterGraph.Node(n, GetterGraph.Kind.SINGULAR, findGetter(rootType, n), attr.getBindableJavaType(), null));
        return this;
    }

    public <A> EntityGraphBuilder<T> fetch(SingularAttribute<? super T, A> attr, Consumer<Sub<A>> nested) {
        jpaGraph.addAttributeNodes(attr.getName());
        var sg = (Subgraph<A>) rootSubgraphCache.computeIfAbsent(attr.getName(), n -> jpaGraph.addSubgraph(n));
        var node = access.roots.computeIfAbsent(attr.getName(), n -> new GetterGraph.Node(n, GetterGraph.Kind.SINGULAR, findGetter(rootType, n), attr.getBindableJavaType(), null));
        nested.accept(new Sub<>(sg, node));
        return this;
    }

    public <E> EntityGraphBuilder<T> fetch(PluralAttribute<? super T, ?, E> attr) {
        jpaGraph.addAttributeNodes(attr.getName());
        access.roots.computeIfAbsent(attr.getName(), n -> new GetterGraph.Node(n, GetterGraph.Kind.COLLECTION, findGetter(rootType, n), attr.getElementType().getJavaType(), null));
        return this;
    }

    public <E> EntityGraphBuilder<T> fetch(PluralAttribute<? super T, ?, E> attr, Consumer<Sub<E>> nested) {
        jpaGraph.addAttributeNodes(attr.getName());
        var sg = (Subgraph<E>) rootSubgraphCache.computeIfAbsent(attr.getName(), n -> jpaGraph.addSubgraph(n));
        var node = access.roots.computeIfAbsent(attr.getName(), n -> new GetterGraph.Node(n, GetterGraph.Kind.COLLECTION, findGetter(rootType, n), attr.getElementType().getJavaType(), null));
        nested.accept(new Sub<>(sg, node));
        return this;
    }

    public <K, V> EntityGraphBuilder<T> fetch(MapAttribute<? super T, K, V> attr) {
        jpaGraph.addAttributeNodes(attr.getName());
        access.roots.computeIfAbsent(attr.getName(), n -> new GetterGraph.Node(n, GetterGraph.Kind.MAP, findGetter(rootType, n), attr.getElementType().getJavaType(), attr.getKeyJavaType()));
        return this;
    }

    public <K, V> EntityGraphBuilder<T> fetch(MapAttribute<? super T, K, V> attr, BiConsumer<Sub<K>, Sub<V>> nested) {
        jpaGraph.addAttributeNodes(attr.getName());
        var keySg = (Subgraph<K>) rootKeySubgraphCache.computeIfAbsent(attr.getName(), n -> jpaGraph.addKeySubgraph(n));
        var valSg = (Subgraph<V>) rootSubgraphCache.computeIfAbsent(attr.getName(), n -> jpaGraph.addSubgraph(n));

        var node = access.roots.computeIfAbsent(attr.getName(), n -> new GetterGraph.Node(n, GetterGraph.Kind.MAP, findGetter(rootType, n), attr.getElementType().getJavaType(), attr.getKeyJavaType()));
        nested.accept(new Sub<>(keySg, node, true), new Sub<>(valSg, node, false));
        return this;
    }

    /* ---------------- Build ---------------- */

    public BuiltGraph<T> build() {
        addRootBasicAttributesToGetterGraph();
        return new BuiltGraph<>(jpaGraph, access);
    }

    /* ---------------- Nested subgraph builder ---------------- */

    public static final class Sub<X> {
        private final Subgraph<X> jpaSub;
        private final GetterGraph.Node parent;
        private final boolean mapKeySide; // only for map branches

        Sub(Subgraph<X> jpaSub, GetterGraph.Node parent) {
            this(jpaSub, parent, false);
        }

        Sub(Subgraph<X> jpaSub, GetterGraph.Node parent, boolean mapKeySide) {
            this.jpaSub = jpaSub;
            this.parent = parent;
            this.mapKeySide = mapKeySide;
        }

        public <A> Sub<X> fetch(SingularAttribute<? super X, A> attr) {
            jpaSub.addAttributeNodes(attr.getName());
            addChild(attr.getName(), GetterGraph.Kind.SINGULAR, attr.getBindableJavaType());
            return this;
        }

        public <A> Sub<X> fetch(SingularAttribute<? super X, A> attr, Consumer<Sub<A>> nested) {
            jpaSub.addAttributeNodes(attr.getName());
            GetterGraph.Node child = addChild(attr.getName(), GetterGraph.Kind.SINGULAR, attr.getBindableJavaType());
            Subgraph<A> sg = jpaSub.addSubgraph(attr.getName());
            nested.accept(new Sub<>(sg, child));
            return this;
        }

        public <E> Sub<X> fetch(PluralAttribute<? super X, ?, E> attr) {
            jpaSub.addAttributeNodes(attr.getName());
            addChild(attr.getName(), GetterGraph.Kind.COLLECTION, attr.getElementType().getJavaType());
            return this;
        }

        public <E> Sub<X> fetch(PluralAttribute<? super X, ?, E> attr, Consumer<Sub<E>> nested) {
            jpaSub.addAttributeNodes(attr.getName());
            GetterGraph.Node child = addChild(attr.getName(), GetterGraph.Kind.COLLECTION, attr.getElementType().getJavaType());
            @SuppressWarnings("unchecked") Subgraph<E> sg = (Subgraph<E>) jpaSub.addSubgraph(attr.getName());
            nested.accept(new Sub<>(sg, child));
            return this;
        }

        public <K, V> Sub<X> fetch(MapAttribute<? super X, K, V> attr) {
            jpaSub.addAttributeNodes(attr.getName());
            addChild(attr.getName(), GetterGraph.Kind.MAP, attr.getElementType().getJavaType(), attr.getKeyJavaType());
            return this;
        }

        public <K, V> Sub<X> fetch(MapAttribute<? super X, K, V> attr, BiConsumer<Sub<K>, Sub<V>> nested) {
            jpaSub.addAttributeNodes(attr.getName());
            GetterGraph.Node child = addChild(attr.getName(), GetterGraph.Kind.MAP, attr.getElementType().getJavaType(), attr.getKeyJavaType());

            Subgraph<K> keySg = jpaSub.addKeySubgraph(attr.getName());
            Subgraph<V> valSg = jpaSub.addSubgraph(attr.getName());

            nested.accept(new Sub<>(keySg, child, true), new Sub<>(valSg, child, false));
            return this;
        }

        private GetterGraph.Node addChild(String name, GetterGraph.Kind kind, Class<?> type) {
            return addChild(name, kind, type, null);
        }

        private GetterGraph.Node addChild(String name, GetterGraph.Kind kind, Class<?> type, Class<?> keyType) {
            Map<String, GetterGraph.Node> bucket = mapKeySide ? parent.keyChildren : parent.children;
            return bucket.computeIfAbsent(name, n -> new GetterGraph.Node(n, kind, findGetter(ownerType(), n), type, keyType));
        }

        private Class<?> ownerType() {
            return mapKeySide ? parent.mapKeyType : parent.javaType;
        }
    }


    /* ---------------- util: resolve getters ---------------- */

    private static Method findGetter(Class<?> owner, String name) {
        String cap = Character.toUpperCase(name.charAt(0)) + name.substring(1);
        try {
            Method m = owner.getMethod(String.format("get%s", cap));
            m.setAccessible(true);
            return m;
        } catch (NoSuchMethodException e) {
            try {
                Method m = owner.getMethod(String.format("is%s", cap));
                m.setAccessible(true);
                return m;
            } catch (Exception ex) {
                throw new IllegalStateException(String.format("No getter found for %s.%s", owner.getName(), name));
            }
        }
    }
}
