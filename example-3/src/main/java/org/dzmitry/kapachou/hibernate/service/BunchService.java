package org.dzmitry.kapachou.hibernate.service;

import lombok.AllArgsConstructor;
import org.dzmitry.kapachou.hibernate.jpa.AbstractCrudService;
import org.dzmitry.kapachou.hibernate.jpa.BaseRepository;
import org.dzmitry.kapachou.hibernate.model.Bunch;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
@AllArgsConstructor
public class BunchService extends AbstractCrudService<Bunch> {

    private final BunchRepository bunchRepository;

    @Override
    protected BaseRepository<Bunch> getRepository() {
        return bunchRepository;
    }

    @Override
    public Collection<Bunch> findAll() {
        return bunchRepository.findAll();
    }
}
