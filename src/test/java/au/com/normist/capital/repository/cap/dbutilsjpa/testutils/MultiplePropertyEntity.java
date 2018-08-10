package au.com.normist.capital.repository.cap.dbutilsjpa.testutils;

import au.com.normist.capital.core.annotation.cap.PersistEntity;

import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.util.Date;

@PersistEntity
public class MultiplePropertyEntity {

  @Id
  public Long id;
  
  public String name;

  public int age;

  @Temporal(TemporalType.DATE)
  public Date birthDate;
}
