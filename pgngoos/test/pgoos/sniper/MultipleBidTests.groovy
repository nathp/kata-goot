package pgoos.sniper;


import static org.mockito.Mockito.*
import junit.framework.TestCase
import pgoos.sniper.events.Bid

public class MultipleBidTests extends TestCase {

    FakeAuctionHouse server
    Sniper sniper
    AuctionStateListener ui

    void setUp() {
        ui = mock(AuctionStateListener.class)
        sniper = new Sniper(clientId: "thisclient", auctions: new Auctions(listener: ui, clientId: "thisclient"))
        server = new FakeAuctionHouse(sniper)

        sniper.start()
    }

    void test_should_be_able_to_auto_bid_multiple_items() {
        ["item1", "item2"].each {server.sendWelcome(it)}
        server.mimicBid "item1",  "100", "clientA"
        server.mimicBid "item2",  "101", "clientB"
        verify(ui).bidUpdate Bid.newBid("item1", "100", "clientA")
        verify(ui).bidUpdate Bid.newBid("item2", "101", "clientB")
    }

}
