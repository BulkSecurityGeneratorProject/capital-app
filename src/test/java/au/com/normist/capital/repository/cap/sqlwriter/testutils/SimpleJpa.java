package au.com.normist.capital.repository.cap.sqlwriter.testutils;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "simple_with_jpa")
public class SimpleJpa {

  @Id
  private Long id;

  @Column(name = "name_with_jpa")
  private String name;

  @ManyToMany(mappedBy = "bidirectionalManyToManyOwner")
  private Set<ParentToMany> bidirectionalManyToMany;
}
