package org.dzmitry.kapachou.hibernate.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;

@Data
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "feedback")
@DiscriminatorValue("feedback")
public class Feedback extends Commentable {

    private double rate;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "profile_id", nullable = false, unique = true)
    private Profile profile;
}
