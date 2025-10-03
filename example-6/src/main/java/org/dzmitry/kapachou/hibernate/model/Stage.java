package org.dzmitry.kapachou.hibernate.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
@Table(name = "stage")
@Entity
public class Stage extends IdEntity{

    private String title;
    private String description;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "project_id", nullable = false)
    private Project project;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "stage_brigade",
            joinColumns = @JoinColumn(name = "stage_id"),
            inverseJoinColumns = @JoinColumn(name = "brigade_id"))
    private List<Brigade> brigades = new ArrayList<>();


}
