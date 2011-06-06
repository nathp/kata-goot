package pgoos.sniper.events.auctionstates

import pgoos.sniper.AuctionStateListener
import pgoos.sniper.events.Close
import pgoos.sniper.Auction

/**
 * Copy right of Prasanth Nath.
 * Date: 6/6/11, Time: 4:06 PM
 * Do not use without permission.
 */
class Closing {
    def handle(Auction auction, Close close, AuctionStateListener listener) {
        if (close.isResponseToOurBid(auction)) {
            listener.auctionLost close
        } else {
            listener.won close
        }
        auction.close()
    }
}
