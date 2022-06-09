package org.dzmitry.kapachou.hibernate.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.CascadeType;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.NamedAttributeNode;
import javax.persistence.NamedEntityGraph;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.Collections;
import java.util.List;
import lombok.ToString;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table
@Entity
@NamedEntityGraph(
        name = "graph.training.sessions",
        attributeNodes = @NamedAttributeNode(value = "sessions")
)
@ToString
public class Training extends IdEntity {

    private String name;

    private String description;

    @Convert(converter = StatusConverter.class)
    private Status status;

    //unidirectional association
    @OneToMany(cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
    @JoinColumn(name = "training_id")
    List<Session> sessions = Collections.emptyList();

}
