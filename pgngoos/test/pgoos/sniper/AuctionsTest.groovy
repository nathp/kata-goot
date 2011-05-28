package pgoos.sniper;
import org.junit.*
import static org.mockito.Mockito.*
import junit.framework.TestCase

public class AuctionsTest extends TestCase {

    void test_creates_fresh_Auction() {
        assertNotNull new Auctions().autionFor("unknown")
    }

    void test_does_not_create_new_Auction_when_already_exists() {
        Auctions auctions = new Auctions()
        assertSame auctions.autionFor("someId"), auctions.autionFor("someId")
    }
}
