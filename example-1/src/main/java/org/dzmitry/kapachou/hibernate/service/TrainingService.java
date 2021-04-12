package org.dzmitry.kapachou.hibernate.service;

import lombok.AllArgsConstructor;
import org.dzmitry.kapachou.hibernate.jpa.AbstractCrudService;
import org.dzmitry.kapachou.hibernate.jpa.BaseRepository;
import org.dzmitry.kapachou.hibernate.model.Training;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class TrainingService extends AbstractCrudService<Training> {

    private final TrainingRepository trainingRepository;

    @Override
    protected BaseRepository<Training> getRepository() {
        return trainingRepository;
    }
}
