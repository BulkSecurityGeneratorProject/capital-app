package au.com.normist.capital.repository.cap.sqlwriter.builders;

import au.com.normist.capital.repository.cap.sqlwriter.testutils.Simple;
import au.com.normist.capital.repository.cap.sqlwriter.testutils.Uninsertable;
import au.com.normist.capital.repository.cap.sqlwriter.testutils.UninsertableProperties;
import org.junit.Test;

import static au.com.normist.capital.repository.cap.sqlwriter.Queries.update;
import static org.junit.Assert.assertEquals;

public class UpdateBuilderTest {

  @Test
  public void should_update_table() {
    assertEquals("UPDATE Simple SET Simple.name = ?, Simple.other = ?", update(Simple.class).set("name", "other").toString());
  }

  @Test
  public void should_filter_and_qualify() {
    String sql = update(Simple.class).set("name").limit(3).desc("other").asc("name").where().eq("other").toString();
    assertEquals("UPDATE Simple SET Simple.name = ? WHERE Simple.other = ? ORDER BY Simple.other DESC, Simple.name ASC LIMIT 3", sql);
  }

  @Test
  public void sql_should_alias_toString() {
    UpdateBuilder update = update(Simple.class).set("name");
    assertEquals(update.toString(), update.sql());
  }

  @Test
  public void should_add_all_columns() {
    String sql = update(Simple.class).sql();
    assertEquals("UPDATE Simple SET Simple.id = ?, Simple.name = ?", sql);
  }

  @Test
  public void should_not_add_unupdatable_columns() {
    String sql = update(Uninsertable.class).sql();
    assertEquals("UPDATE Uninsertable SET Uninsertable.name = ?, Uninsertable.age = ?", sql);
  }

  @Test
  public void should_not_add_unupdatable_columns_from_properties() {
    String sql = update(UninsertableProperties.class).sql();
    assertEquals("UPDATE UninsertableProperties SET UninsertableProperties.age = ?, UninsertableProperties.name = ?", sql);
  }
}
