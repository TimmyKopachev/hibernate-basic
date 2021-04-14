package org.dzmitry.kapachou.hibernate.service;

import org.dzmitry.kapachou.hibernate.jpa.BaseRepository;
import org.dzmitry.kapachou.hibernate.model.Attender;
import org.dzmitry.kapachou.hibernate.model.AttenderTask;
import org.dzmitry.kapachou.hibernate.model.AttenderTaskPK;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Collection;

@Repository
public interface AttenderRepository extends BaseRepository<Attender> {

    @Override
    @EntityGraph(value = "graph.attender.tasks", type = EntityGraph.EntityGraphType.LOAD)
    Collection<Attender> findAll();


    @EntityGraph(value = "graph.attender_task", type = EntityGraph.EntityGraphType.LOAD)
    @Query("""
            SELECT at FROM AttenderTask at WHERE id = :#{#attenderTaskPK}
            """)
    Collection<AttenderTask> findAllByPK(@Param("attenderTaskPK") AttenderTaskPK attenderTaskPK);

}
