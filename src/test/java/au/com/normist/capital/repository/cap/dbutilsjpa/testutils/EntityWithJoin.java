package au.com.normist.capital.repository.cap.dbutilsjpa.testutils;

import au.com.normist.capital.core.annotation.cap.PersistEntity;

import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

@PersistEntity
public class EntityWithJoin {

  @Id
  public Long id;

  @ManyToOne @JoinColumn(name = "simple_id")
  public SimpleEntity simple;

  @ManyToOne @JoinColumn(name = "custom_enum_fk")
  public EnumEntity enumEntity;

  @OneToOne
  public MultiplePropertyEntity oneToOne;

  @ManyToOne
  public NonUpdatableEntity customSuffix;
}
