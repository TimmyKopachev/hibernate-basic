package org.dzmitry.kapachou.hibernate.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "brigade")
public class Brigade extends IdEntity {

    private String name;

    @OneToMany(mappedBy = "brigade", fetch = FetchType.LAZY)
    private List<Task> tasks;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "brigade_employee",
            joinColumns = @JoinColumn(name = "brigade_id"),
            inverseJoinColumns = @JoinColumn(name = "client_id")
    )
    private List<Client> employees;

    @ManyToMany(mappedBy = "brigades", fetch = FetchType.LAZY)
    private List<Stage> stages = new ArrayList<>();
}
