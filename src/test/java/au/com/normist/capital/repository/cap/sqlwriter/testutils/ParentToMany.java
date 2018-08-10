package au.com.normist.capital.repository.cap.sqlwriter.testutils;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

public class ParentToMany {

  @Id
  private Long id;

  @OneToMany
  private List<SimpleOneToMany> many;

  @ManyToMany
  private Set<Simple> manyToMany;

  @ManyToMany
  private Set<SimpleJpa> bidirectionalManyToManyOwner;

  @ManyToMany
  @JoinTable(name = "ptm_pk", joinColumns = @JoinColumn(name = "p_2_many_fk"), inverseJoinColumns = @JoinColumn(name = "pk_id_fk"))
  private Set<PkId> pkIds;
}
