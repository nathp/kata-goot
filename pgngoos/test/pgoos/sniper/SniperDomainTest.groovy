package pgoos.sniper

import static org.mockito.Mockito.*
import junit.framework.TestCase

public class SniperDomainTest extends TestCase {

    MessageHandler auction
    StateListener ui
    Sniper sniper


    void setUp() {
        ui = mock(StateListener.class)
        auction = sniper = new Sniper(listener: ui, id: "thisclient")
    }

    void test_sniper_should_receive_welcome_event_when_it_joins_an_auction() {

        FakeServer server = new FakeServer(auction)

        sniper.start()

        server.sendWelcome("someauction")

        verify(ui).connectedNewAuction(Message.from("B1:1.1:someauction:Welcome"))
    }

    void test_should_join_an_action_and_see_bid_updates() {
        FakeServer server = startFakeServer()
        server.sendWelcome("someitem")
        server.mimicBid("someitem", "123", "someclient")
        verify(ui).connectedNewAuction(Message.from("B1:1.1:someitem:Welcome"))
        verify(ui).bidUpdate(Message.from("B1:1.1:someitem:Bid:123:someclient"))
    }

    private FakeServer startFakeServer() {
        FakeServer server = new FakeServer(auction)
        sniper.start()
        server
    }

    void test_should_join_an_auction_and_loose_when_auction_closes() {
        FakeServer server = startFakeServer()
        server.sendWelcome("someitem")
        server.mimicBid("someitem", "123", "someclient")
        server.closeAuction("someitem", "124", "someotherclient")
        verify(ui).auctionLost(Message.from("B1:1.1:someitem:Close:124:someotherclient"))
    }
}
