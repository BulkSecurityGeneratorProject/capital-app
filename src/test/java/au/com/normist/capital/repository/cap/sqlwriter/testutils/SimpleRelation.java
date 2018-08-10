package au.com.normist.capital.repository.cap.sqlwriter.testutils;

import javax.persistence.Id;
import javax.persistence.ManyToOne;

public class SimpleRelation {

  @Id
  private Long id;

  @ManyToOne
  private Simple simple;
}
