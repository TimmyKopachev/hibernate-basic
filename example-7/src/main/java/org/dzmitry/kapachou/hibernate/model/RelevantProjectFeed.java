package org.dzmitry.kapachou.hibernate.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Collection;

@Data
@AllArgsConstructor
public class RelevantProjectFeed {

    private Client client;

    private Collection<Project> projects;
}
