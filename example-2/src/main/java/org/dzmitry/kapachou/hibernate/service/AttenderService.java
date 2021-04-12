package org.dzmitry.kapachou.hibernate.service;

import lombok.AllArgsConstructor;
import org.dzmitry.kapachou.hibernate.jpa.AbstractCrudService;
import org.dzmitry.kapachou.hibernate.jpa.BaseRepository;
import org.dzmitry.kapachou.hibernate.model.Attender;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class AttenderService extends AbstractCrudService<Attender> {

    private final AttenderRepository attenderRepository;

    @Override
    protected BaseRepository<Attender> getRepository() {
        return attenderRepository;
    }
}
