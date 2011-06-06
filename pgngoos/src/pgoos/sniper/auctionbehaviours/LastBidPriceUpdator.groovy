package pgoos.sniper.auctionbehaviours

import pgoos.sniper.Auction
import pgoos.sniper.events.AuctionEvent
import pgoos.sniper.AuctionStateListener
import pgoos.sniper.events.Bid

/**
 * Copy right of Prasanth Nath.
 * Date: 6/6/11, Time: 5:23 PM
 * Do not use without permission.
 */
class LastBidPriceUpdator extends Chain {
    String ourLastBidPrice

    @Override
    Processing doHandle(Auction auction, AuctionEvent msg, AuctionStateListener auctionStateListener) {
        if (isResponseUpdateForOurOwnBid(msg, auction)) {
            ourLastBidPrice = msg.price
        }
        Processing.CONTINUE
    }

    boolean isResponseUpdateForOurOwnBid(AuctionEvent event, Auction auction) {
        event instanceof Bid && event.client == auction.clientId
    }

    boolean firstBidUpdate() {
        return ourLastBidPrice == null
    }

    int price() {
        ourLastBidPrice as int
    }
}
