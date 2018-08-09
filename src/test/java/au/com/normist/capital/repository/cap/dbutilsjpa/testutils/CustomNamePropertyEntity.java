package au.com.normist.capital.repository.cap.dbutilsjpa.testutils;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name="myTableName")
public class CustomNamePropertyEntity {

  private Long id;

  private Date date;
  
  @Id
  @Column(name="customNameId")
  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  @Column(name="customDateColumn")
  @Temporal(TemporalType.DATE)
  public Date getDate() {
    return date;
  }

  public void setDate(Date date) {
    this.date = date;
  }
}
