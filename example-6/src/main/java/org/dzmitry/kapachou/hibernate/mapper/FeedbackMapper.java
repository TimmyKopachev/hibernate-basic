package org.dzmitry.kapachou.hibernate.mapper;

import org.dzmitry.kapachou.hibernate.model.Feedback;
import org.dzmitry.kapachou.hibernate.service.graph.BuiltGraph;
import org.dzmitry.kapachou.hibernate.service.graph.GetterGraphApplier;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public abstract class FeedbackMapper {

    @BeanMapping(ignoreByDefault = true)
    public abstract Feedback toDto(Feedback source,
                                   @Context BuiltGraph<Feedback> graph);

    @AfterMapping
    public void applyGraph(Feedback source,
                           @MappingTarget Feedback target, @Context BuiltGraph<Feedback> graph) {
        GetterGraphApplier.apply(source, target, graph.access());
    }


    @ObjectFactory
    Feedback emptyDto(@TargetType Class<Feedback> type) {
        return new Feedback();
    }


}


