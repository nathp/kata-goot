package pgoos.sniper

import static org.mockito.Mockito.*
import junit.framework.TestCase
import pgoos.sniper.events.Bid
import pgoos.sniper.events.NewAuction

public class SniperDomainTest extends TestCase {

    AuctionStateListener ui
    Sniper sniper

    FakeServer server

    void setUp() {
        ui = mock(AuctionStateListener.class)
        sniper = new Sniper(clientId: "thisclient", auctions: new Auctions(listener: ui, clientId: "thisclient"))
        server = new FakeServer(sniper)
        sniper.start()
    }

    void test_sniper_should_receive_welcome_event_when_it_joins_an_auction() {
        server.sendWelcome("someauction")
        verify(ui).connectedNewAuction(new NewAuction(auctionId:"someauction"))
    }

    void test_should_join_an_action_and_see_bid_updates() {
        server.sendWelcome("someitem")
        server.mimicBid("someitem", "123", "someclient")
        verify(ui).connectedNewAuction(new NewAuction(auctionId:"someitem"))
        verify(ui).bidUpdate(new Bid(auctionId: "someitem", price:"123", client: "someclient"))
    }

    private FakeServer startFakeServer() {
        new FakeServer(sniper)
    }

    void test_should_join_an_auction_and_loose_when_auction_closes() {
        server.sendWelcome("someitem")
        server.mimicBid("someitem", "123", "someclient")
        server.closeAuction("someitem", "124", "someotherclient")
        verify(ui).auctionLost(Message.parseFrom("B1:1.1:someitem:Close:124:someotherclient"))
    }

    void test_should_report_when_bid_is_won() {
        sniper.auctionConnection = mock(AuctionConnection.class)
        server.sendWelcome("someitem")
        server.mimicBid("someitem", "123", "someclient")
        sniper.bid("someitem", "124")
        server.closeAuction "someitem", "124", "thisclient"
        verify(sniper.auctionConnection).sendMessage "B1:1.1:someitem:Bid:124:thisclient"
        verify(ui).won(Message.parseFrom("B1:1.1:someitem:Close:124:thisclient"))
    }

    void test_should_update_bids_as_loosing_ones_when_other_bid_higher() {
        sniper.auctionConnection = mock(AuctionConnection.class)
        server.sendWelcome("someitem")
        bidFor("someitem", "10")

        server.mimicBid("someitem", "11", "someclient")
        verify(ui).loosing(new Bid(auctionId: "someitem", price: "11", client: "someclient", state: Bid.State.Losing))
    }

    private def bidFor(String someitem, String price) {
        sniper.bid(someitem, price)
        server.mimicBid(someitem, price, myclientId())
    }

    String myclientId() {
        "thisclient"
    }
}
