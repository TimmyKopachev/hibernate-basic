package org.dzmitry.kapachou.hibernate.model;

import java.util.List;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
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
@Table(name = "project")

@ToString(exclude = "employees")
public class Project extends IdEntity {

  private String name;

  @ManyToMany(fetch = FetchType.LAZY)
  @JoinTable(
      name = "employee_project",
      joinColumns = {@JoinColumn(name = "project_id")},
      inverseJoinColumns = {@JoinColumn(name = "employee_id")})
  private List<Employee> employees;

}
