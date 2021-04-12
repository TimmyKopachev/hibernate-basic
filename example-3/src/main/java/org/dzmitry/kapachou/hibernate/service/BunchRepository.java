package org.dzmitry.kapachou.hibernate.service;

import org.dzmitry.kapachou.hibernate.jpa.BaseRepository;
import org.dzmitry.kapachou.hibernate.model.Bunch;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Collection;

@Repository
public interface BunchRepository extends BaseRepository<Bunch> {

    @Override
    @Query(value = """
            SELECT DISTINCT b 
            FROM Bunch AS b
            LEFT OUTER JOIN FETCH b.studentIds
            """)
    Collection<Bunch> findAll();
}
