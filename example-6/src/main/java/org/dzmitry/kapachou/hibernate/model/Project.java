package org.dzmitry.kapachou.hibernate.model;


import lombok.Data;

import javax.persistence.*;
import java.util.List;
import java.util.stream.Collectors;

@Data
@Entity
@Table(name = "project")
public class Project extends IdEntity {

    private String title;
    private String description;

    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    @JoinColumn(name = "owner_id", nullable = false)
    private Client owner;

    @ManyToMany(mappedBy = "brigades",
            targetEntity = Brigade.class,
            fetch = FetchType.EAGER,
            cascade = {CascadeType.ALL})
    private List<Brigade> brigades;

//    @ManyToMany(mappedBy = "projectsAssigned",
//            targetEntity = Client.class,
//            fetch = FetchType.EAGER,
//            cascade = {CascadeType.ALL})
//    private List<Client> brigade;

}

