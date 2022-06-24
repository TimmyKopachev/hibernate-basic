package org.dzmitry.kapachou.hibernate.service;

import org.dzmitry.kapachou.hibernate.model.Project;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProjectRepository extends JpaRepository<Project, Long> {


}
