package pgoos.sniper

import junit.framework.TestCase
import pgoos.sniper.events.Bid
import pgoos.sniper.events.Close
import pgoos.sniper.events.NewAuction
import static org.mockito.Mockito.*

public class SniperAutoBidTest extends TestCase {

    AuctionStateListener ui
    Sniper sniper
    AuctionConnection connection

    FakeAuctionHouse fakeServer

    void setUp() {
        ui = mock(AuctionStateListener.class)
        connection = mock(AuctionConnection)
        sniper = new Sniper(clientId: "thisclient", auctions: new Auctions(listener: ui, clientId: "thisclient"),
                auctionConnection: connection)
        fakeServer = new FakeAuctionHouse(sniper)
        sniper.start()
    }

    void test_auto_bid_sends_an_automatic_bid_at_higher_price() {
        fakeServer.sendWelcome "someitem"
        sniper.autoBid(AutoBid.forItem("someitem").increment(1).stopAt(110))
        fakeServer.mimicBid "someitem", "90", myclientId()
        verify(connection).sendMessage AuctionMessage.rawBidMessage("someitem", "91", myclientId())
    }

    void test_auto_bid_when_barrier_is_crossed() {
        fakeServer.sendWelcome "someitem"
        fakeServer.mimicBid "someitem", "90", myclientId()
        sniper.autoBid(AutoBid.forItem("someitem").increment(1).stopAt(110))

        fakeServer.mimicBid "someitem", "111", myclientId()
        verify(ui).exceededStopPrice(new Bid("someitem", "111", myclientId(), Bid.State.StopPriceBreached))
    }

    private def bidFor(String someitem, String price) {
        sniper.bid(someitem, price)
        fakeServer.mimicBid(someitem, price, myclientId())
    }

    String myclientId() {
        "thisclient"
    }
}
