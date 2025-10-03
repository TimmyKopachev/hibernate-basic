package org.dzmitry.kapachou.hibernate.service;

import org.dzmitry.kapachou.hibernate.mapper.FeedbackMapper;
import org.dzmitry.kapachou.hibernate.model.Feedback;
import org.dzmitry.kapachou.hibernate.service.graph.EntityGraphBuilder;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;

@Service

public class FeedbackService extends AbstractService {

    private final FeedbackMapper feedbackMapper;

    public FeedbackService(EntityManager entityManager, FeedbackMapper feedbackMapper) {
        super(entityManager);
        this.feedbackMapper = feedbackMapper;
    }

    public Feedback getFeedback(Long feedbackId, EntityGraphBuilder<Feedback> graphBuilder) {
        var builtGraph = graphBuilder.build();
        var loaded = loadEntityGraphs(
                feedbackId,
                Feedback.class, builtGraph.jpa(),
                EntityGraph.EntityGraphType.FETCH.getKey());
        return feedbackMapper.toDto(loaded, builtGraph);
    }


}
