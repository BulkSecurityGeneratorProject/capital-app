package au.com.normist.capital.repository.cap.sqlwriter.testutils;

import au.com.normist.capital.core.annotation.cap.PersistEntity;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import java.util.Set;

@PersistEntity
@Table(name = "simple_with_jpa")
public class SimpleJpa {

  @Id
  private Long id;

  @Column(name = "name_with_jpa")
  private String name;

  @ManyToMany(mappedBy = "bidirectionalManyToManyOwner")
  private Set<ParentToMany> bidirectionalManyToMany;
}
