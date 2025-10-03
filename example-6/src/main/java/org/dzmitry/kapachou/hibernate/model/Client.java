package org.dzmitry.kapachou.hibernate.model;


import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "client")
public class Client extends IdEntity {

    private String name;

    @OneToOne(mappedBy = "client",
            fetch = FetchType.LAZY,
            cascade = CascadeType.ALL,
            orphanRemoval = true)
    private Profile profile;

    @OneToMany(mappedBy = "owner", fetch = FetchType.LAZY)
    private List<Project> projectsOwned;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "client_assigned_projects",
            joinColumns = @JoinColumn(name = "client_id"),
            inverseJoinColumns = @JoinColumn(name = "project_id")
    )
    private List<Project> projectsAssigned = new ArrayList<>();

}
