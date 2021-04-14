package org.dzmitry.kapachou.hibernate.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.CascadeType;
import javax.persistence.EmbeddedId;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.NamedAttributeNode;
import javax.persistence.NamedEntityGraph;
import javax.persistence.Table;

@Data
@NoArgsConstructor
@AllArgsConstructor
@javax.persistence.Entity
@Table(name = "attender_task")

@NamedEntityGraph(
        name = "graph.attender_task",
        attributeNodes = {
                @NamedAttributeNode(value = "attender"),
                @NamedAttributeNode(value = "task"),
        }

)
public class AttenderTask {

    @EmbeddedId
    private AttenderTaskPK attenderTaskPK;

    @Enumerated(EnumType.STRING)
    private Status status;

    //ignore json recursive output
    @JsonIgnore
    /////////////
    @ManyToOne(cascade = CascadeType.ALL)
    @MapsId("attenderId")
    private Attender attender;

    @ManyToOne(cascade = CascadeType.ALL)
    @MapsId("taskId")
    private Task task;

}
