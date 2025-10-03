package org.dzmitry.kapachou.hibernate.model;


import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "profile")
public class Profile extends IdEntity {

    private String description;


    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "client_id", nullable = false, unique = true)
    private Client client;


    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "profile_skill",
            joinColumns = @JoinColumn(name = "profile_id"),
            inverseJoinColumns = @JoinColumn(name = "skill_id")
    )
    private List<Skill> skills;


    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "profile_feedback",
            joinColumns = @JoinColumn(name = "profile_id"),
            inverseJoinColumns = @JoinColumn(name = "feedback_id")
    )
    private List<Feedback> feedbacks;

}
