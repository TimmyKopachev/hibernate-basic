package org.dzmitry.kapachou.hibernate.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.time.Instant;

@Data
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "task")
@DiscriminatorValue("task")
public class Task extends Commentable {

    private String description;

    private Instant softDeadline;
    private Instant hardDeadline;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "brigade_id", nullable = false)
    private Brigade brigade;

}
