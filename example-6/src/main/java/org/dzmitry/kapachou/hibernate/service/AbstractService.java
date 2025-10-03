package org.dzmitry.kapachou.hibernate.service;

import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.persistence.EntityManager;
import java.util.Map;


@AllArgsConstructor
public abstract class AbstractService {
    @Getter
    protected final EntityManager entityManager;

    public <T> T loadEntityGraphs(
            Long entityId,
            Class<T> entityClass,
            javax.persistence.EntityGraph<T> rootGraph,
            String entityGraphType) {
        return entityManager.find(
                entityClass,
                entityId,
                Map.of(entityGraphType, rootGraph));
    }
}
