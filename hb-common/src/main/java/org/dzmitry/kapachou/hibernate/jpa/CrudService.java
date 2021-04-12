package org.dzmitry.kapachou.hibernate.jpa;

import org.dzmitry.kapachou.hibernate.model.IdEntity;

import java.util.Collection;

public interface CrudService<T extends IdEntity> {

    T save(T t);

    T update(T t);

    T get(Long id);

    void delete(Long id);

    Collection<T> findAll();

}
