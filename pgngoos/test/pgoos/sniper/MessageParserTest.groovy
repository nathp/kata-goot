package pgoos.sniper;
import org.junit.*
import static org.mockito.Mockito.*
import junit.framework.TestCase

public class MessageParserTest extends TestCase {
    void test_parses_msg_id() {
        assertEquals "someid", new MessageParser().parseId("B1:1.1:someid:Welcome")
    }

    void test_close_message() {
        Message m = new MessageParser().parse("B1:1.1:someItem:Close:123:someClient")
        assertEquals "123", m.column(4)
        assertFalse m.isBid()
        assertTrue m.isLose("myClient")
        assertFalse m.isLose("someClient")

    }

}
