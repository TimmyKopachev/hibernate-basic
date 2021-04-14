package org.dzmitry.kapachou.hibernate.model;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@Embeddable
public class AttenderTaskPK implements Serializable {

    @Column(name = "attender_id")
    private Long attenderId;

    @Column(name = "task_id")
    private Long taskId;

}
