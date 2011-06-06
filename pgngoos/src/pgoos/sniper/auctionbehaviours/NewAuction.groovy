package pgoos.sniper.auctionbehaviours

import pgoos.sniper.Auction
import pgoos.sniper.AuctionStateListener
import pgoos.sniper.events.AuctionEvent

/**
 * Copy right of Prasanth Nath.
 * Date: 6/6/11, Time: 4:36 PM
 * Do not use without permission.
 */
class NewAuction implements Behaviour {

    @Override
    Processing handle(Auction auction, AuctionEvent event, AuctionStateListener listener) {
        def handle = event instanceof pgoos.sniper.events.NewAuction
        if (handle) {
            listener.connectedNewAuction event as pgoos.sniper.events.NewAuction
        }
        handle ? Processing.STOP : Processing.CONTINUE
    }

}
