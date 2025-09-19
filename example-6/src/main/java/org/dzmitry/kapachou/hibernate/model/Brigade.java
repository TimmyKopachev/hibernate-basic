package org.dzmitry.kapachou.hibernate.model;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
@Table(name = "brigade")
public class Brigade extends IdEntity{

    private String name;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "stage_id", nullable = false)
    private Stage stage;

    @ManyToMany(mappedBy = "brigade")
    private List<Task> tasks;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "brigade_employee",
            joinColumns = @JoinColumn(name = "brigade_id"),
            inverseJoinColumns = @JoinColumn(name = "client_id")
    )
    private List<Client> employee;

    @ManyToMany(mappedBy = "brigades")
    private List<Stage> stages;
}
