package au.com.normist.capital.repository.cap.dbutilsjpa.testutils;

import au.com.normist.capital.core.annotation.cap.PersistEntity;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;

@PersistEntity
@Table(catalog="catalog")
public class EmptyNamePropertyEntity {

  @Id
  @Column(nullable=false)
  public Long getId() {
    return null;
  }
}
