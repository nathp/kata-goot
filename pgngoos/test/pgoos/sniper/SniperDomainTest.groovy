package pgoos.sniper

import org.junit.Test
import org.junit.Before;
import static org.mockito.Mockito.*
import junit.framework.TestCase


public class SniperDomainTest extends TestCase {

    Auction auction
    StateListener ui


    void setUp() {
        auction = mock(Auction.class)
        ui = mock(StateListener.class)
    }

    void test_sniper_should_receive_welcome_event_when_it_joins_an_auction() {
        Sniper sniper = new Sniper(auction: auction, listener: ui)
        when(auction.register()).thenReturn(Event.Welcome("someauction"))

        sniper.start()

        verify(ui).connectedNewAuction("someauction")
    }
}
