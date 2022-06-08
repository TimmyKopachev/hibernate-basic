package hibernate.model;

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
import lombok.ToString;
import org.dzmitry.kapachou.hibernate.model.IdEntity;

@ToString(exclude = {"parent", "units"})
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
}
