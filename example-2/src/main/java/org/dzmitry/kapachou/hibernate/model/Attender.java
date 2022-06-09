package org.dzmitry.kapachou.hibernate.model;


import java.util.Set;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.NamedAttributeNode;
import javax.persistence.NamedEntityGraph;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.Formula;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table
@Entity
@NamedEntityGraph(
        name = "graph.attender.tasks",
        attributeNodes = @NamedAttributeNode(value = "tasks")
)
@ToString(of = {"fullName"})
public class Attender extends IdEntity {

/*
    @Column(name = "first_name")
    private String firstName;
    @Column(name = "last_name")
    private String lastName;
*/

    @Formula(value = "concat(first_name, ' ', last_name)")
    private String fullName;

    @OneToMany(mappedBy = "attender", fetch = FetchType.LAZY)
    Set<AttenderTask> tasks;

}
