package org.dzmitry.kapachou.hibernate.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Table;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table
@Entity
public class Session extends IdEntity {

    private String topic;

    //option to create bidirectional association
    //@ManyToOne(fetch = FetchType.LAZY)
    //private Training training;

}
