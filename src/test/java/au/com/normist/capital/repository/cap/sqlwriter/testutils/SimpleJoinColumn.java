package au.com.normist.capital.repository.cap.sqlwriter.testutils;

import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import java.util.List;

public class SimpleJoinColumn {

  @Id
  private Long id;

  @OneToMany
  private List<Simple> simples;

  @ManyToOne
  @JoinColumn(name = "pk_fk")
  private PkId pkId;
}
