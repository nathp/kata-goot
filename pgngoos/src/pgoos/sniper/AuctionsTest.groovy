package pgoos.sniper;
import org.junit.*
import static org.mockito.Mockito.*
import junit.framework.TestCase

public class AuctionsTestTest extends TestCase {

    void test_creates_fresh_Auction() {
        assertNotNull new Auctions().autionFor("unknown")
    }
}
