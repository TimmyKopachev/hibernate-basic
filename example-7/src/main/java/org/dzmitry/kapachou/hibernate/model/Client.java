package org.dzmitry.kapachou.hibernate.model;


import lombok.Data;
import org.locationtech.jts.geom.Point;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Data
@Entity
@Table(name = "client")
public class Client extends IdEntity {

    private String name;
    private String location;

    private Integer radius;

    @Column(columnDefinition = "geometry(Point, 4326)", nullable = false)
    private Point position;

}
