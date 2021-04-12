package org.dzmitry.kapachou.hibernate.service;

import lombok.AllArgsConstructor;
import org.dzmitry.kapachou.hibernate.jpa.AbstractCrudService;
import org.dzmitry.kapachou.hibernate.jpa.BaseRepository;
import org.dzmitry.kapachou.hibernate.model.Session;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class SessionService extends AbstractCrudService<Session> {

    private final SessionRepository sessionRepository;

    @Override
    protected BaseRepository<Session> getRepository() {
        return sessionRepository;
    }
}
