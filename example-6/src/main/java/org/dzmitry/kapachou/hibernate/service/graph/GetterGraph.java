package org.dzmitry.kapachou.hibernate.service.graph;

import lombok.AllArgsConstructor;

import java.lang.reflect.Method;
import java.util.LinkedHashMap;
import java.util.Map;

public class GetterGraph<T> {

    public enum Kind {SINGULAR, COLLECTION, MAP}

    @AllArgsConstructor
    public static final class Node {
        public final String name;
        public final Kind kind;
        public final Method getter;            // getter on the *owner* type (pre-resolved)
        public final Class<?> javaType;        // for singular: type; for collection: element type; for map: value type
        public final Class<?> mapKeyType;      // for map only
        public final Map<String, Node> children = new LinkedHashMap<>(); // subgraph for singular/collection element / map value
        public final Map<String, Node> keyChildren = new LinkedHashMap<>(); // map key subgraph

    }

    public final Class<T> rootType;
    public final Map<String, Node> roots = new LinkedHashMap<>();

    GetterGraph(Class<T> rootType) {
        this.rootType = rootType;
    }
}
