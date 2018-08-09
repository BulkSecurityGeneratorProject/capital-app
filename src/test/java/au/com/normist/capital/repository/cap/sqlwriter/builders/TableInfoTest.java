package au.com.normist.capital.repository.cap.sqlwriter.builders;

import au.com.normist.capital.repository.cap.sqlwriter.dialects.StandardDialect;
import au.com.normist.capital.repository.cap.sqlwriter.mapping.ColumnInfo;
import au.com.normist.capital.repository.cap.sqlwriter.mapping.TableInfo;
import au.com.normist.capital.repository.cap.sqlwriter.testutils.EmptyNames;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class TableInfoTest {

  @Test
  public void should_ignore_empty_table_annotation() {
    TableInfo table = new TableInfo(EmptyNames.class, new StandardDialect());

    assertEquals("EmptyNames", table.name);
  }

  @Test
  public void should_ignore_empty_column_name_annotation() {
    TableInfo table = new TableInfo(EmptyNames.class, new StandardDialect());
    ColumnInfo column = table.column("emptyColumnName");

    assertEquals(table.name + ".emptyColumnName", column.toString());
  }
}
