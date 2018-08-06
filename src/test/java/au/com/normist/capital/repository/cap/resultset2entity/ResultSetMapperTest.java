package au.com.normist.capital.repository.cap.resultset2entity;

import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import java.sql.ResultSet;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

//TODO test with d2
public class ResultSetMapperTest {

//    private MockitoResultSetBuilder resultSetBuilder;
    private ResultSet resultSet;

    ResultSetMapper<TestEntityForResultSetMap> resultSetMapper;


    @Before
    public void setUp() throws Exception {
//        resultSetBuilder = new MockitoResultSetBuilder();
//        resultSet = resultSetBuilder.withPath("src/test/resources/data/jdbc-data.csv").build();

        resultSetMapper = new ResultSetMapper<>();
    }

    @After
    public void tearDown() throws Exception {
    }

    @Ignore
    @Test
    public void mapResultSetToObject() {
        List<TestEntityForResultSetMap> actualObjectList = resultSetMapper.mapResultSetToObject(resultSet, TestEntityForResultSetMap.class);

        assertThat(actualObjectList).isNotNull();
        assertThat(actualObjectList.size()).isEqualTo(6);


    }



}
