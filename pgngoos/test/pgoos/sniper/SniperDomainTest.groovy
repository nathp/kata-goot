package pgoos.sniper

import static org.mockito.Mockito.*
import junit.framework.TestCase

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
        verify(ui).connectedNewAuction(Message.parseFrom("B1:1.1:someauction:Welcome"))
    }

    void test_should_join_an_action_and_see_bid_updates() {
        server.sendWelcome("someitem")
        server.mimicBid("someitem", "123", "someclient")
        verify(ui).connectedNewAuction(Message.parseFrom("B1:1.1:someitem:Welcome"))
        verify(ui).bidUpdate(Message.parseFrom("B1:1.1:someitem:Bid:123:someclient"))
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
}
