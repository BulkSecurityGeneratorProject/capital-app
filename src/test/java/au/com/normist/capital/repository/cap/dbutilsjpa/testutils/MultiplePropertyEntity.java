package au.com.normist.capital.repository.cap.dbutilsjpa.testutils;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.util.Date;

@Entity
public class MultiplePropertyEntity {

  @Id
  public Long id;
  
  public String name;

  public int age;

  @Temporal(TemporalType.DATE)
  public Date birthDate;
}
