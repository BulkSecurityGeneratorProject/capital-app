package au.com.normist.capital.repository.cap.dbutilsjpa.testutils;

import au.com.normist.capital.core.annotation.cap.PersistEntity;

import javax.persistence.Id;

@PersistEntity
public class EnumEntity {

  public static enum SomeEnum {
    VALUE_1, VALUE_2;
  }
  
  @Id
  public Long id;
  
  public SomeEnum anEnum;
}
