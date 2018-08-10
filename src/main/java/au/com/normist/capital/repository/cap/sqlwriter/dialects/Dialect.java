package au.com.normist.capital.repository.cap.sqlwriter.dialects;


import au.com.normist.capital.repository.cap.sqlwriter.builders.QualifierBuilder;
import au.com.normist.capital.repository.cap.sqlwriter.builders.SelectWhereBuilder;
import au.com.normist.capital.repository.cap.sqlwriter.builders.WhereBuilder;
import au.com.normist.capital.repository.cap.sqlwriter.mapping.ColumnInfo;
import au.com.normist.capital.repository.cap.sqlwriter.mapping.TableInfo;

import java.util.List;

public interface Dialect {
  String select(TableInfo rootTable, List<TableInfo> tables, List<SelectWhereBuilder> wheres, QualifierBuilder qualifier);
  String join(String type, TableInfo root, ColumnInfo from, ColumnInfo to);
  String column(TableInfo table, String name, String alias);
  String function(String name, TableInfo table, String column, String alias);
  String table(TableInfo table);
  String insert(TableInfo table, List<ColumnInfo> columns);
  String update(TableInfo table, List<ColumnInfo> columns, WhereBuilder where, QualifierBuilder qualifiers);
  String delete(TableInfo table, WhereBuilder where, QualifierBuilder qualifier);
  String qualify(String order, int limit, int offset);
}
