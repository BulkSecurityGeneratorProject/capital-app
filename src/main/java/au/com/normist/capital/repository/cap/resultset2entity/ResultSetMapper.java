package au.com.normist.capital.repository.cap.resultset2entity;

import au.com.normist.capital.core.annotation.cap.PersistEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.Column;
import java.lang.reflect.Field;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ResultSetMapper<T> {
    private final Logger log = LoggerFactory.getLogger(ResultSetMapper.class);

    @SuppressWarnings("unchecked")
    public List<T> mapResultSetToObject(ResultSet rs, Class outputClass) {

        List<T> outputList = null;
        try {
            // make sure resultset is not null
            if (rs != null) {
                // check if outputClass has 'Entity' annotation
                if (outputClass.isAnnotationPresent(PersistEntity.class)) {
                    // get the resultset metadata
                    ResultSetMetaData rsmd = rs.getMetaData();
                    // get all the attributes of outputClass
                    Field[] fields = outputClass.getDeclaredFields();
                    while (rs.next()) {
                        T bean = (T) outputClass.newInstance();
                        for (int _iterator = 0; _iterator < rsmd
                            .getColumnCount(); _iterator++) {
                            // getting the SQL column name
                            String columnName = rsmd
                                .getColumnName(_iterator + 1);
                            // reading the value of the SQL column
                            Object columnValue = rs.getObject(_iterator + 1);
                            // iterating over outputClass attributes to check if any attribute has 'Column' annotation with matching 'name' value
                            for (Field field : fields) {
                                if (field.isAnnotationPresent(Column.class)) {
                                    Column column = field
                                        .getAnnotation(Column.class);
                                    if (column.name().equalsIgnoreCase(
                                        columnName)
                                        && columnValue != null) {

                                        field.setAccessible(true);
                                        field.set(bean, columnValue);
//                                        BeanUtils.setProperty(bean, field.getName(), columnValue);
                                        break;
                                    }
                                }
                            }
                        }
                        if (outputList == null) {
                            outputList = new ArrayList<>();
                        }
                        outputList.add(bean);
                    }

                } else {
                    // throw some error
                }
            } else {
                return Collections.emptyList();
            }
        } catch (IllegalAccessException | SQLException | InstantiationException e) {
            log.error("error translate resultset to object", e);
        }
        return outputList;
    }
}
