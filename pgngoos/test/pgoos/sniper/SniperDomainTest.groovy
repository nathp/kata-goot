package pgoos.sniper

import static org.mockito.Mockito.*
import junit.framework.TestCase
import pgoos.sniper.events.Bid
import pgoos.sniper.events.NewAuction
import pgoos.sniper.events.Close

public class SniperDomainTest extends TestCase {

    AuctionStateListener ui
    Sniper sniper

    FakeServer fakeServer

    void setUp() {
        ui = mock(AuctionStateListener.class)
        sniper = new Sniper(clientId: "thisclient", auctions: new Auctions(listener: ui, clientId: "thisclient"))
        fakeServer = new FakeServer(sniper)
        sniper.start()
    }

    void test_sniper_should_receive_welcome_event_when_it_joins_an_auction() {
        fakeServer.sendWelcome("someauction")
        verify(ui).connectedNewAuction(new NewAuction(auctionId:"someauction"))
    }

    void test_should_join_an_action_and_see_bid_updates() {
        fakeServer.sendWelcome("someitem")
        fakeServer.mimicBid("someitem", "123", "someclient")
        verify(ui).connectedNewAuction(new NewAuction(auctionId:"someitem"))
        verify(ui).bidUpdate(Bid.newBid("someitem", "123", "someclient"))
    }

    private FakeServer startFakeServer() {
        new FakeServer(sniper)
    }

    void test_should_join_an_auction_and_loose_when_auction_closes() {
        fakeServer.sendWelcome("someitem")
        fakeServer.mimicBid("someitem", "123", "someclient")
        fakeServer.closeAuction("someitem", "124", "someotherclient")
        verify(ui).connectedNewAuction(new NewAuction(auctionId:"someitem"))
        verify(ui).bidUpdate(Bid.newBid("someitem", "123", "someclient"))
        verify(ui).auctionLost(new Close(auctionId :"someitem", price:"124", client:"someotherclient"))
        verifyNoMoreInteractions ui
    }

    void test_should_call_auction_server_when_bidding() {
        sniper.auctionConnection = mock(AuctionConnection.class)
        fakeServer.sendWelcome("someitem")
        sniper.bid("someitem", "124")
        verify(ui).connectedNewAuction(new NewAuction(auctionId:"someitem"))
        verify(sniper.auctionConnection).sendMessage "B1:1.1:someitem:Bid:124:thisclient"
        verifyNoMoreInteractions ui
    }

    void test_should_report_when_bid_is_won() {
        fakeServer.sendWelcome("someitem")
        fakeServer.mimicBid("someitem", "123", "someclient")
        sniper.bid("someitem", "124")
        fakeServer.closeAuction "someitem", "124", "thisclient"
        verify(ui).won(new Close(auctionId:"someitem", price:"124", client:"thisclient"))
    }

    void test_should_update_bids_as_loosing_ones_when_somebody_else_bids_higher() {
        fakeServer.sendWelcome("someitem")
        bidFor("someitem", "10")

        fakeServer.mimicBid("someitem", "11", "someclient")
        verify(ui).loosing(Bid.newBid("someitem", "11", "someclient").loosing())
    }

    private def bidFor(String someitem, String price) {
        sniper.bid(someitem, price)
        fakeServer.mimicBid(someitem, price, myclientId())
    }

    String myclientId() {
        "thisclient"
    }
}
