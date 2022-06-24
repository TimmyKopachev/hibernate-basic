package org.dzmitry.kapachou.hibernate.model;

import java.util.Iterator;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true, exclude = {"parent", "units"})
@Data
@Entity
@Table(name = "unit")
public class Unit extends IdEntity {

  private String name;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "parent_id", referencedColumnName = "id")
  private Unit parent;

  @OneToMany(fetch = FetchType.EAGER, mappedBy = "parent", cascade = CascadeType.ALL, orphanRemoval = true)
  private Set<Unit> units;

  public String toString() {
    StringBuilder buffer = new StringBuilder(50);
    return print(buffer, "", "");
  }

  private String print(StringBuilder buffer, String prefix, String childrenPrefix) {
    buffer.append(prefix);
    buffer.append(name);
    buffer.append('\n');
    for (Iterator<Unit> it = units.iterator(); it.hasNext(); ) {
      Unit next = it.next();
      if (it.hasNext()) {
        next.print(buffer, childrenPrefix + "├── ", childrenPrefix + "│   ");
      } else {
        next.print(buffer, childrenPrefix + "└── ", childrenPrefix + "    ");
      }
    }
    return buffer.toString();
  }
}
