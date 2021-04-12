package org.dzmitry.kapachou.hibernate.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table
@Entity
public class Task extends IdEntity {

    private String description;

    //ignore json recursive output
    @JsonIgnore
    /////////////
    @OneToMany(mappedBy = "task", fetch = FetchType.EAGER)
    Set<AttenderTask> tasks;

}
