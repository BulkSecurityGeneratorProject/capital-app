package au.com.normist.capital.repository.cap.dbutilsjpa.testutils;

import au.com.normist.capital.core.annotation.cap.PersistEntity;

import javax.persistence.Id;

@PersistEntity
public class SimplePropertyEntity {

  private Long id;

  @Id
  public Long getId() {
    return id;
  }
  
  public void setId(Long id) {
    this.id = id;
  }
}
