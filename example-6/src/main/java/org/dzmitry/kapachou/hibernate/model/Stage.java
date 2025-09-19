package org.dzmitry.kapachou.hibernate.model;

import javax.persistence.*;
import java.util.List;

@Table(name = "stage")
@Entity
public class Stage extends IdEntity{

    private String title;
    private String description;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "project_id", nullable = false)
    private Project project;

    @ManyToMany
    @JoinTable(name = "stage_brigade",
            joinColumns = @JoinColumn(name = "stage_id"),
            inverseJoinColumns = @JoinColumn(name = "brigade_id"))
    private List<Brigade> brigades;


}
