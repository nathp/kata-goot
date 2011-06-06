package pgoos.sniper;


import junit.framework.TestCase

public class AuctionsTest extends TestCase {

    void test_creates_fresh_Auction_instance_for_new_identity() {
        assertNotNull new Auctions().findAuctionWithId("unknown")
    }

    void test_does_not_create_new_Auction_when_it_already_exists() {
        Auctions auctions = new Auctions()
        assertSame auctions.findAuctionWithId("someId"), auctions.findAuctionWithId("someId")
    }
}
