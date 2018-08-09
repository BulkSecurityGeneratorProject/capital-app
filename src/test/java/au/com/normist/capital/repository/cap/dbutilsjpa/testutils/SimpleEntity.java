package au.com.normist.capital.repository.cap.dbutilsjpa.testutils;

import au.com.normist.capital.core.annotation.cap.PersistEntity;

import javax.persistence.Id;

@PersistEntity
public class SimpleEntity {

  private static final Object CONSTANT = new Object(); 
  
  @Id
  private Long id;
  private String name;

  public Long getId() {
    return id;
  }
  
  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }
}
