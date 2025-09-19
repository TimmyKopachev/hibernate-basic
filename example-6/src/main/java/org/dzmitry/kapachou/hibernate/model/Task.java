package org.dzmitry.kapachou.hibernate.model;

import lombok.Data;

import javax.persistence.*;
import java.time.Instant;

@Data
@Entity
@Table(name = "task")
public class Task extends IdEntity {

    private String description;

    private Instant softDeadline;
    private Instant hardDeadline;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "brigade_id", nullable = false)
    private Brigade brigade;
}
