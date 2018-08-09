package au.com.normist.capital.repository.cap.dbutilsjpa;

import au.com.normist.capital.repository.cap.dbutilsjpa.testutils.CustomNamePropertyEntity;
import au.com.normist.capital.repository.cap.dbutilsjpa.testutils.SimpleEntity;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class SqlWriterDeleteTest {
  private SqlWriter sqlWriter = new SqlWriter();

  @Test
  public void should_delete() {
    String sql = sqlWriter.deleteById(SimpleEntity.class);

    assertEquals("DELETE FROM SimpleEntity WHERE SimpleEntity.id = ?", sql);
  }

  @Test
  public void should_use_annotations_on_property() {
    String sql = sqlWriter.deleteById(CustomNamePropertyEntity.class);

    assertEquals("DELETE FROM myTableName WHERE myTableName.customNameId = ?", sql);
  }

}
