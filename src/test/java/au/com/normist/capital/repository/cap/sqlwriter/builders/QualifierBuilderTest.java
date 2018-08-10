package au.com.normist.capital.repository.cap.sqlwriter.builders;

import au.com.normist.capital.repository.cap.sqlwriter.testutils.Simple;
import org.junit.Test;

import static au.com.normist.capital.repository.cap.sqlwriter.Queries.select;
import static org.junit.Assert.assertEquals;

public class QualifierBuilderTest {

  private final SelectBuilder select = select().from(Simple.class);

  @Test
  public void should_limit() {
    assertEquals(select + " LIMIT 10", select().from(Simple.class).limit(10).sql());
  }

  @Test
  public void should_order_ascending() {
    assertEquals(select + " ORDER BY Simple.name ASC LIMIT 10", select.limit(10).asc("name").sql());
  }
}
