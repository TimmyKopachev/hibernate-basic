package org.dzmitry.kapachou.hibernate.service.graph;

import javax.persistence.EntityGraph;

public class BuiltGraph<T> {

    private final EntityGraph<T> jpaGraph;
    private final GetterGraph<T> getterGraph;

    BuiltGraph(EntityGraph<T> jpaGraph, GetterGraph<T> getterGraph) {
        this.jpaGraph = jpaGraph;
        this.getterGraph = getterGraph;
    }

    public EntityGraph<T> jpa() { return jpaGraph; }
    public GetterGraph<T> access() { return getterGraph; }

}
