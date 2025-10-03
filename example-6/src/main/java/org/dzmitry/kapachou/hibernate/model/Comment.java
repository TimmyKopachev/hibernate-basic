package org.dzmitry.kapachou.hibernate.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.time.Instant;

@Data
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "comment")
public class Comment extends IdEntity {

    private String comment;
    private Instant createdAt;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "author_id", nullable = false)
    private Client author;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "owner_id", nullable = false)
    private Commentable commentable;


}
