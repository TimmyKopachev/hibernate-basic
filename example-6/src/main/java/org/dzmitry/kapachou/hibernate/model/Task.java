package org.dzmitry.kapachou.hibernate.model;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.time.Instant;

@Data
@Entity
@Table(name = "task")
public class Task {

    private String description;

    private Instant softDeadline;
    private Instant hardDeadline;
}
