package org.dzmitry.kapachou.hibernate.service;

import org.dzmitry.kapachou.hibernate.jpa.BaseRepository;
import org.dzmitry.kapachou.hibernate.model.Attender;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.stereotype.Repository;

import java.util.Collection;

@Repository
public interface AttenderRepository extends BaseRepository<Attender> {

/*    @Override
    @EntityGraph(value = "graph.attender.tasks", type = EntityGraph.EntityGraphType.LOAD)
    Collection<Attender> findAll();*/
}
