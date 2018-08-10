package au.com.normist.capital.repository.cap.sqlwriter.testutils;

import javax.persistence.Id;
import javax.persistence.OneToOne;

public class BiDirectionalOneToOne1 {

  @Id
  private Long id;

  @OneToOne(mappedBy = "biDirectionalOneToOne1")
  private BiDirectionalOneToOne2 biDirectionalOneToOne2;
}
