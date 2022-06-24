package org.dzmitry.kapachou.hibernate.model;

import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PUBLIC)
@Data
@Entity
@Table(name = "employee")

//@ToString(exclude = "projects")
public class Employee extends IdEntity {

  private String name;
  private String email;

  @ManyToMany(mappedBy = "employees", fetch = FetchType.EAGER, cascade = {CascadeType.ALL})
  private List<Project> projects;

}
