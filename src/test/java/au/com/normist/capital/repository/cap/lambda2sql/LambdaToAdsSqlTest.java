package au.com.normist.capital.repository.cap.lambda2sql;

import org.danekja.java.util.function.serializable.SerializablePredicate;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.assertEquals;


public class LambdaToAdsSqlTest {

    @BeforeClass
    public static void init() throws Exception {
        LambdaToAdsSql.init();
    }

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }


    @Test
    public void testComparisons() throws Exception {
        assertEqual("age = 1", e -> e.getAge() == 1);
        assertEqual("age > 1", e -> e.getAge() > 1);
        assertEqual("age < 1", e -> e.getAge() < 1);
        assertEqual("age >= 1", e -> e.getAge() >= 1);
        assertEqual("age <= 1", e -> e.getAge() <= 1);
        assertEqual("age != 1", e -> e.getAge() != 1);
    }

    @Test
    public void testLogicalOps() throws Exception {
        assertEqual("!isActive", e -> ! e.isActive() );
        assertEqual("age < 100 AND height > 200", e -> e.getAge() < 100 && e.getHeight() > 200 );
        assertEqual("age < 100 OR height > 200", e -> e.getAge() < 100 || e.getHeight() > 200 );
    }

    @Test
    public void testMultipleLogicalOps() throws Exception {
        assertEqual("isActive AND (age < 100 OR height > 200)", e -> e.isActive() && (e.getAge() < 100 || e.getHeight() > 200) );
        assertEqual("(age < 100 OR height > 200) AND isActive", e -> (e.getAge() < 100 || e.getHeight() > 200) && e.isActive() );
    }


    private void assertEqual(String expectedSql, SerializablePredicate<TestPerson> p) {
        String sql = LambdaToAdsSql.convertToAdsSql(p);
        assertEquals(expectedSql, sql);
    }
}
