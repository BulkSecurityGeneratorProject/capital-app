package au.com.normist.capital.repository.cap.sqlwriter.builders;


import au.com.normist.capital.repository.cap.sqlwriter.dialects.Dialect;
import au.com.normist.capital.repository.cap.sqlwriter.mapping.ColumnInfo;
import au.com.normist.capital.repository.cap.sqlwriter.mapping.TableInfo;

import java.util.ArrayList;
import java.util.List;

public class InsertBuilder {

  private final TableInfo table;
  private final List<ColumnInfo> columns = new ArrayList<>();
  private final Dialect dialect;

  public InsertBuilder(Class<?> entityClass, Dialect dialect) {
    this.table = new TableInfo(entityClass, dialect);
    this.dialect = dialect;
  }

  public InsertBuilder columns(String... columns) {
    for (String column : columns) {
      this.columns .add(table.column(column));
    }
    return this;
  }

  public String sql() {
    return toString();
  }

  @Override
  public String toString() {
    return dialect.insert(table, columns);
  }
}
