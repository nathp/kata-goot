package pgoos.sniper.events.auctionstates

import pgoos.sniper.Auction
import pgoos.sniper.events.Close
import pgoos.sniper.AuctionStateListener
import pgoos.sniper.events.Bid

/**
 * Copy right of Prasanth Nath.
 * Date: 6/6/11, Time: 4:08 PM
 * Do not use without permission.
 */
class BidState {
    def handle(Auction auction, Bid bid, AuctionStateListener listener) {
        if (auction.firstBidUpdate()) {
            listener.bidUpdate bid
        } else if (auction.exceededStopPrice(bid)) {
            listener.exceededStopPrice bid.asStopPriceBreached()
        } else if (auction.isLoosing(bid)) {
            listener.loosing bid.loosing()
        }
        else {
            listener.bidUpdate bid.winning()
        }
    }
}
