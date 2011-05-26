package pgoos.sniper

import org.junit.Test
import org.junit.Before;
import static org.mockito.Mockito.*
import junit.framework.TestCase


public class SniperDomainTest extends TestCase {

    Auction auction
    StateListener ui
    Sniper sniper


    void setUp() {
        auction = new DefaultAuction()
        ui = mock(StateListener.class)
        sniper = new Sniper(auction: auction, listener: ui, id:"thisclient")


        auction.registerEventListener sniper
    }

    void test_sniper_should_receive_welcome_event_when_it_joins_an_auction() {

        FakeServer server = new FakeServer(auction)

        sniper.start()

        server.sendWelcome("someauction")

        verify(ui).connectedNewAuction(Event.newAuction("someauction"))
    }

    void test_should_join_an_action_and_see_bid_updates() {
        FakeServer server = new FakeServer(auction)
        sniper.start()
        server.sendWelcome("someclient")
        server.mimicBid("someitem", "123", "someclient")
        verify(ui).connectedNewAuction(Event.newAuction("someclient"))
        verify(ui).bidUpdate(Event.Bid("someitem", "123", "someclient"))
    }
}
