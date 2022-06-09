package org.dzmitry.kapachou.hibernate.model;


import java.util.Set;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table
@Entity
@ToString(of = "description")
public class Task extends IdEntity {

    private String description;

    //ignore json recursive output
    /////////////
    @OneToMany(mappedBy = "task", fetch = FetchType.EAGER)
    Set<AttenderTask> tasks;

}
