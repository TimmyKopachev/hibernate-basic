package org.dzmitry.kapachou.hibernate.model;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
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
public class Attender extends IdEntity {

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

/*    @ElementCollection(fetch = FetchType.LAZY)
    @CollectionTable(name = "attender_task", joinColumns = @JoinColumn(name = "attender_id"))
    @Column(name = "task_id")
    List<Long> taskIds = Collections.emptyList();*/

    @OneToMany(mappedBy = "attender", fetch = FetchType.EAGER)
    Set<AttenderTask> tasks;

}
