package pgoos.sniper;
import org.junit.*
import static org.mockito.Mockito.*
import junit.framework.TestCase

public class MessageParserTest extends TestCase {
    void test_parses_msg_id() {
        assertEquals "someid", new MessageParser().parseId("B1:1.1:someid:Welcome")
    }

    void test_fetching_values_from_columns_in_message() {
        Message m = new MessageParser().parse("B1:1.1:someItem:Close:123: someClient")
        assertEquals "123", m.column(4)
        assertEquals "someClient", m.column(5)
        assertEquals null, m.column(6)

    }

}
