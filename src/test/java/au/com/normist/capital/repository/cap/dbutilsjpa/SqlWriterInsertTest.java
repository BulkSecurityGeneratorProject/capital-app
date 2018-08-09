package au.com.normist.capital.repository.cap.dbutilsjpa;

import au.com.normist.capital.repository.cap.dbutilsjpa.testutils.CustomNamePropertyEntity;
import au.com.normist.capital.repository.cap.dbutilsjpa.testutils.SimpleEntity;
import au.com.normist.capital.repository.cap.dbutilsjpa.testutils.Utils;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class SqlWriterInsertTest {

  private final SqlWriter sqlWriter = new SqlWriter();

  @Test
  public void should_use_field() {
    String sql = sqlWriter.insert(SimpleEntity.class);

    assertEquals("INSERT INTO SimpleEntity(name) VALUES(?)", Utils.singleLine(sql));
  }

  @Test
  public void should_use_custom_names_from_property() {
    String sql = sqlWriter.insert(CustomNamePropertyEntity.class);

    assertEquals("INSERT INTO myTableName(customDateColumn) VALUES(?)", Utils.singleLine(sql));
  }
}
