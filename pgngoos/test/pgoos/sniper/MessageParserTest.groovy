package pgoos.sniper;
import org.junit.*
import static org.mockito.Mockito.*
import junit.framework.TestCase

public class MessageParserTest extends TestCase {
    void test_parses_msg_id() {
        assertEquals "someid", new MessageParser().parseId("B1:1.1:someid:Welcome")
    }
}
