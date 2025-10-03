package org.dzmitry.kapachou.hibernate.model;


import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "project")
public class Project extends IdEntity {

    private String title;
    private String description;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "owner_id", nullable = false)
    private Client owner;

    @OneToMany(mappedBy = "project", fetch = FetchType.LAZY,
            cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Stage> stages = new ArrayList<>();

}

