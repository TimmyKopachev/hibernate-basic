package org.dzmitry.kapachou.hibernate.service;

import org.dzmitry.kapachou.hibernate.jpa.BaseRepository;
import org.dzmitry.kapachou.hibernate.model.Session;
import org.springframework.stereotype.Repository;

@Repository
public interface SessionRepository extends BaseRepository<Session> {
}
