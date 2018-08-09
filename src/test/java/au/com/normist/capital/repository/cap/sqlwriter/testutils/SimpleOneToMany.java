package au.com.normist.capital.repository.cap.sqlwriter.testutils;

import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import java.util.List;

public class SimpleOneToMany {

  @Id
  private Long id;

  @OneToMany
  private List<Simple> simples;

  @ManyToOne
  private PkId pkId;

  @OneToMany
  @JoinColumn(name = "renamed_one_to_many")
  private List<SimpleJpa> simpleJpas;
}
