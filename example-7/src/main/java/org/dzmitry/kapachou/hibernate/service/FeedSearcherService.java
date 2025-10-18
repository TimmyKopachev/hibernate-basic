package org.dzmitry.kapachou.hibernate.service;

import lombok.AllArgsConstructor;
import org.dzmitry.kapachou.hibernate.model.Project;
import org.dzmitry.kapachou.hibernate.model.RelevantProjectFeed;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;

@Service
@AllArgsConstructor
public class FeedSearcherService {

    private final ProjectFeedRepository projectRepository;
    private final ClientRepository clientRepository;

    @Transactional(readOnly = true)
    public Collection<Project> lookupRelevantProjects(Long clientId) {
        return projectRepository.findProjectsByRadius(clientId);
    }

    @Transactional(readOnly = true)
    public RelevantProjectFeed buildFeedForClient(Long clientId) {
        var client = clientRepository.findById(clientId).orElseThrow();
        Collection<Project> feed = projectRepository.findProjectsByRadius(client.getId());
        return new RelevantProjectFeed(client, feed);
    }
}
