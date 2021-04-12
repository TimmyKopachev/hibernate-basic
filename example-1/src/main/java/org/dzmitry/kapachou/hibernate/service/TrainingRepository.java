package org.dzmitry.kapachou.hibernate.service;

import org.dzmitry.kapachou.hibernate.jpa.BaseRepository;
import org.dzmitry.kapachou.hibernate.model.Training;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.stereotype.Repository;

import java.util.Collection;

@Repository
public interface TrainingRepository extends BaseRepository<Training> {

    @Override
    @EntityGraph(value = "graph.training.sessions", type = EntityGraph.EntityGraphType.LOAD)
    Collection<Training> findAll();

}
