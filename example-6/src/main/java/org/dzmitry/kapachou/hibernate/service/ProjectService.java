package org.dzmitry.kapachou.hibernate.service;

import org.dzmitry.kapachou.hibernate.mapper.ProjectMapper;
import org.dzmitry.kapachou.hibernate.model.Brigade_;
import org.dzmitry.kapachou.hibernate.model.Project;
import org.dzmitry.kapachou.hibernate.model.Project_;
import org.dzmitry.kapachou.hibernate.model.Stage_;
import org.dzmitry.kapachou.hibernate.service.graph.EntityGraphBuilder;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import java.util.Collection;
import java.util.stream.Collectors;

@Service
public class ProjectService extends AbstractService {

    private final ProjectMapper projectMapper;

    public ProjectService(EntityManager entityManager, ProjectMapper projectMapper) {
        super(entityManager);
        this.projectMapper = projectMapper;
    }


    public Project getProject(Long projectId, EntityGraphBuilder<Project> graphBuilder) {
        var builtGraph = graphBuilder.build();
        var loaded = loadEntityGraphs(
                projectId,
                Project.class, builtGraph.jpa(),
                EntityGraph.EntityGraphType.FETCH.getKey());

        return projectMapper.toDto(loaded, builtGraph);
    }

    public Collection<Project> getProjectsOwned() {
        var entityGraph = new EntityGraphBuilder<>(entityManager, Project.class)
                .fetch(Project_.stages,
                        s -> s.fetch(Stage_.brigades,
                                b -> b.fetch(Brigade_.tasks))).build();
        return entityManager
                .createQuery(String.format("select distinct t from %s where p.owner.id =  :ownerId",
                        Project.class.getSimpleName()), Project.class)
                .setParameter("ownerId", 1L)
                .setHint(EntityGraph.EntityGraphType.FETCH.getKey(), entityGraph)
                .getResultStream()
                .map(r -> projectMapper.toDto(r, entityGraph))
                .collect(Collectors.toList());
    }

    public Collection<Project> getProjectsAssigned() {
        var entityGraph = new EntityGraphBuilder<>(entityManager, Project.class)
                .fetch(Project_.stages,
                        s -> s.fetch(Stage_.brigades,
                                b -> b.fetch(Brigade_.tasks))).build();
        return entityManager
                .createQuery(String.format("select distinct t from %s where p.owner.id =  :ownerId",
                        Project.class.getSimpleName()), Project.class)
                .setParameter("ownerId", 1L)
                .setHint(EntityGraph.EntityGraphType.FETCH.getKey(), entityGraph)
                .getResultStream()
                .map(r -> projectMapper.toDto(r, entityGraph))
                .collect(Collectors.toList());
    }

}
