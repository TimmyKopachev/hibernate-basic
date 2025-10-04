package org.dzmitry.kapachou.hibernate.model;


import lombok.Data;
import org.locationtech.jts.geom.Point;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Data
@Entity
@Table(name = "project")
public class Project extends IdEntity {

    private String title;
    private String description;
    private String address;
    @Column(columnDefinition = "geometry(Point, 4326)", nullable = false)
    private Point position;
}

