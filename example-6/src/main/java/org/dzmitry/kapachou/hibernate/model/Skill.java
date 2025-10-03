package org.dzmitry.kapachou.hibernate.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Entity;
import javax.persistence.Table;

@Data
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "skill")
public class Skill extends IdEntity {

    private String name;
    private Integer level;
}
