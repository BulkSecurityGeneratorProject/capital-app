package au.com.normist.capital.repository.cap.sqlwriter.testutils;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.Set;

public class PropertyAccess {

  @Id
  public Long getId() {
    return null;
  }

  @Column(name = "name_property")
  public String getName() {
    return null;
  }

  @OneToMany
  public Set<Simple> getSimples() {
    return null;
  }
}
