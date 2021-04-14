package org.dzmitry.kapachou.hibernate.service;

import lombok.AllArgsConstructor;
import org.dzmitry.kapachou.hibernate.jpa.AbstractCrudService;
import org.dzmitry.kapachou.hibernate.jpa.BaseRepository;
import org.dzmitry.kapachou.hibernate.model.Attender;
import org.dzmitry.kapachou.hibernate.model.AttenderTask;
import org.dzmitry.kapachou.hibernate.model.AttenderTaskPK;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
@AllArgsConstructor
public class AttenderService extends AbstractCrudService<Attender> {

    private final AttenderRepository attenderRepository;


    public Collection<AttenderTask> findAllByPK(Long attenderId, Long taskId) {
        return attenderRepository.findAllByPK(new AttenderTaskPK(attenderId, taskId));
    }

    @Override
    protected BaseRepository<Attender> getRepository() {
        return attenderRepository;
    }
}
