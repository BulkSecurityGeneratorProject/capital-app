package au.com.normist.capital.repository.cap.sqlwriter.testutils;

import javax.persistence.Id;
import javax.persistence.OneToOne;

public class BiDirectionalOneToOne2 {

  @Id
  private Long id;

  @OneToOne
  private BiDirectionalOneToOne1 biDirectionalOneToOne1;
}
