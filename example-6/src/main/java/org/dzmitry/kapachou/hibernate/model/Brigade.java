package org.dzmitry.kapachou.hibernate.model;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.List;

@Data
@Entity
@Table(name = "brigade")
public class Brigade extends IdEntity{

    private String name;

    private List<Client> employee;

    private List<Task> tasks;
}
