package pgoos.sniper.auctionbehaviours

import pgoos.sniper.AuctionStateListener

import pgoos.sniper.Auction
import pgoos.sniper.events.AuctionEvent
import pgoos.sniper.events.Close

/**
 * Copy right of Prasanth Nath.
 * Date: 6/6/11, Time: 4:06 PM
 * Do not use without permission.
 */
class Closing extends Chain {
    @Override
    Processing doHandle(Auction auction, AuctionEvent close, AuctionStateListener listener) {
        def handle = close instanceof Close
        if (handle) {
            if (close.isResponseToOurBid(auction)) {
                listener.auctionLost close
            } else {
                listener.won close
            }
            auction.close()
        }
        Processing.CONTINUE

    }
}
