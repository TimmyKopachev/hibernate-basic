package org.dzmitry.kapachou.hibernate.model;


import lombok.Data;
import lombok.ToString;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
@Table(name = "client")

@ToString(exclude = "owner")
public class Client extends IdEntity {

    private String name;

    @OneToMany(mappedBy = "owner", fetch = FetchType.EAGER)
    private List<Project> projectsOwned;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "project_brigade",
            joinColumns = {@JoinColumn(name = "client_id")},
            inverseJoinColumns = {@JoinColumn(name = "project_id")})
    private List<Project> projectsAssigned;

}
