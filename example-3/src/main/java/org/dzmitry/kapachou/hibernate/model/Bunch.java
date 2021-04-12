package org.dzmitry.kapachou.hibernate.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.Table;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table
@Entity
public class Bunch extends IdEntity {

    private String name;

    @ElementCollection(fetch = FetchType.LAZY)
    // associate tables
    @CollectionTable(name = "student", joinColumns = @JoinColumn(name = "bunch_id"))
    //fetch column `id` (can be name, type and etc)
    @Column(name = "id")
    List<Long> studentIds;
}
