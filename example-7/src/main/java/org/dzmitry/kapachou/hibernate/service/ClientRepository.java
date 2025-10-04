package org.dzmitry.kapachou.hibernate.service;

import org.dzmitry.kapachou.hibernate.model.Client;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClientRepository extends JpaRepository<Client, Long> {

}
