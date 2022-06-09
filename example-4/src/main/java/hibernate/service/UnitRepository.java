package hibernate.service;

import hibernate.model.Unit;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UnitRepository extends JpaRepository<Unit, Long> {

  @Override
  @Query(value = """
       WITH RECURSIVE units_tree (id, name, parent_id) AS (
       SELECT id, name, parent_id FROM unit
         UNION ALL
       SELECT u.id, u.name, u.parent_id FROM unit u
       INNER JOIN units_tree ut ON ut.id = u.parent_id  )
       SELECT id, name, parent_id
      FROM units_tree
      WHERE parent_id IS NULL
      """, nativeQuery = true)
  List<Unit> findAll();
}
