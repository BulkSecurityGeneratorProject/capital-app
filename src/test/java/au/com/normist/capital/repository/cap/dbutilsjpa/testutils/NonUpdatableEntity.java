package au.com.normist.capital.repository.cap.dbutilsjpa.testutils;

import au.com.normist.capital.core.annotation.cap.PersistEntity;

import javax.persistence.Column;
import javax.persistence.Id;

@PersistEntity
public class NonUpdatableEntity {

  @Id
  private Long id;

  private String name;

  @Column(updatable=false)
  private String notUpdated;

  @Column(insertable=false)
  private String notInserted;

  public void setId(Long id) {
    this.id = id;
  }

  public Long getId() {
    return id;
  }
  
  public String getName() {
    return name;
  }
  
  public void setName(String name) {
    this.name = name;
  }

  public String getNotUpdated() {
    return notUpdated;
  }

  public void setNotUpdated(String notUpdated) {
    this.notUpdated = notUpdated;
  }

  public String getNotInserted() {
    return notInserted;
  }

  public void setNotInserted(String notInserted) {
    this.notInserted = notInserted;
  }
}
