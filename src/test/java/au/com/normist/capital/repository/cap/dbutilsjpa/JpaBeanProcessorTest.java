package au.com.normist.capital.repository.cap.dbutilsjpa;

import au.com.normist.capital.repository.cap.dbutilsjpa.testutils.CustomNamePropertyEntity;
import au.com.normist.capital.repository.cap.dbutilsjpa.testutils.EnumEntity;
import au.com.normist.capital.repository.cap.dbutilsjpa.testutils.SimpleEntity;
import au.com.normist.capital.repository.cap.dbutilsjpa.testutils.SimplePropertyEntity;
import org.junit.Before;
import org.junit.Test;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;



public class JpaBeanProcessorTest {

  private final JpaBeanProcessor processor = new JpaBeanProcessor();
  private final ResultSetMetaData metaData = mock(ResultSetMetaData.class);
  private final ResultSet resultSet = mock(ResultSet.class);
  
  @Before
  public void before() throws SQLException {
    when(resultSet.getMetaData()).thenReturn(metaData);
  }
  
  @Test
  public void should_convert_with_annotated_field() throws SQLException {
    when(metaData.getColumnCount()).thenReturn(1);
    int idColumnIndex = 1;
    setColumnName("id", idColumnIndex);

    setLongValue(resultSet, idColumnIndex, 1L);
    
    SimpleEntity entity = processor.toBean(resultSet, SimpleEntity.class);
    
    assertEquals(Long.valueOf(1), entity.getId());
  }
  
  @Test
  public void should_convert_with_annotated_property() throws SQLException {
    int columnIndex = 1;
    when(metaData.getColumnCount()).thenReturn(columnIndex);
    setColumnName("id", columnIndex);

    setLongValue(resultSet, columnIndex, 1L);
    
    SimplePropertyEntity entity = processor.toBean(resultSet, SimplePropertyEntity.class);
    
    assertEquals(Long.valueOf(1), entity.getId());
  }
  
  @Test
  public void should_convert_with_annotated_property_with_custom_names() throws SQLException {
    int idColumnIndex = 1;
    when(metaData.getColumnCount()).thenReturn(1);
    setColumnName("customNameId", idColumnIndex);

    setLongValue(resultSet, idColumnIndex, 3L);
    
    CustomNamePropertyEntity entity = processor.toBean(resultSet, CustomNamePropertyEntity.class);
    
    assertEquals(Long.valueOf(3), entity.getId());
  }
  
  @Test
  public void should_handle_enum() throws SQLException {
    when(metaData.getColumnCount()).thenReturn(2);
    setColumnName("id", 1);
    setColumnName("anEnum", 2);
    
    setLongValue(resultSet, 1, 1L);
    setStringValue(resultSet, 2, EnumEntity.SomeEnum.VALUE_2.name());
    
    EnumEntity entity = processor.toBean(resultSet, EnumEntity.class);
    
    assertEquals(EnumEntity.SomeEnum.VALUE_2, entity.anEnum);
  }

  private void setLongValue(ResultSet resultSet, int columnIndex, Long columnValue) throws SQLException {
    when(resultSet.getObject(columnIndex)).thenReturn(columnValue);
    when(resultSet.getLong(columnIndex)).thenReturn(columnValue);
  }

  private void setStringValue(ResultSet resultSet, int columnIndex, String columnValue) throws SQLException {
    when(resultSet.getObject(columnIndex)).thenReturn(columnValue);
    when(resultSet.getString(columnIndex)).thenReturn(columnValue);
  }

  private void setColumnName(String columnName, int columnIndex) throws SQLException {
    when(metaData.getColumnName(columnIndex)).thenReturn(columnName);
  }
}
