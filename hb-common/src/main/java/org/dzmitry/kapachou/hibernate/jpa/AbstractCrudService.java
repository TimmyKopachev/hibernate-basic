package org.dzmitry.kapachou.hibernate.jpa;

import lombok.extern.slf4j.Slf4j;
import org.dzmitry.kapachou.hibernate.exception.NonExistingEntityException;
import org.dzmitry.kapachou.hibernate.model.IdEntity;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Slf4j
@Transactional
public abstract class AbstractCrudService<T extends IdEntity> implements CrudService<T> {

    @Override
    public T save(T t) {
        log.info("saving {}", t);
        return getRepository().save(t);
    }

    @Override
    public T update(T t) {
        log.info("updating {}", t);
        return getRepository().save(t);
    }

    @Override
    public T get(Long id) {
        log.info("getting by id: {}", id);
        Optional<T> optional = getRepository().findById(id);
        if (optional.isPresent()) {
            return optional.get();
        }
        throw new NonExistingEntityException(String.format("Can not find %s with id %o", this.getClass().getCanonicalName(), id));
    }

    @Override
    public void delete(Long id) {
        log.info("getting by id: {}", id);
        getRepository().deleteById(id);
    }

    @Override
    public Collection<T> findAll() {
        Iterable<T> entities = getRepository().findAll();
        List<T> list = new ArrayList<>();
        entities.forEach(list::add);
        return list;
    }

    protected abstract BaseRepository<T> getRepository();
}
