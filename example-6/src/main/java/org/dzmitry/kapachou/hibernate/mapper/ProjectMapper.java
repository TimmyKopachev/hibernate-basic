package org.dzmitry.kapachou.hibernate.mapper;

import org.dzmitry.kapachou.hibernate.model.Project;
import org.dzmitry.kapachou.hibernate.service.graph.BuiltGraph;
import org.dzmitry.kapachou.hibernate.service.graph.GetterGraphApplier;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public abstract class ProjectMapper {

    @BeanMapping(ignoreByDefault = true)
    public abstract Project toDto(Project source,
                                  @Context BuiltGraph<Project> graph);

    @AfterMapping
    public void applyGraph(Project source,
                           @MappingTarget Project target, @Context BuiltGraph<Project> graph) {
        GetterGraphApplier.apply(source, target, graph.access());
    }


    @ObjectFactory
    Project emptyDto(@TargetType Class<Project> type) {
        return new Project(); // or builder if you use immutable DTOs
    }


}


