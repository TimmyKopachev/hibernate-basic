package org.dzmitry.kapachou.hibernate.service;

import org.dzmitry.kapachou.hibernate.model.Project;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProjectFeedRepository extends JpaRepository<Project, Long> {

    // radius is set in km in tables.
    // ST_DistanceSphere is comparing in meters.
    // => we need to convert client.radius into meters to compare the distance correctly
    @Query(value = """
            SELECT p.* FROM project p 
            JOIN client c ON c.id = :clientId 
            WHERE ST_DistanceSphere(p.position, c.position) <= (c.radius * 1000);
                    """, nativeQuery = true)
    List<Project> findProjectsByRadius(@Param("clientId") Long clientId);
}
