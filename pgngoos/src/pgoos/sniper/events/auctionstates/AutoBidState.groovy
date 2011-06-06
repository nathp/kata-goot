package pgoos.sniper.events.auctionstates

import pgoos.sniper.AuctionStateListener
import pgoos.sniper.events.Bid
import pgoos.sniper.Auction
import pgoos.sniper.AutoBid
import pgoos.sniper.Sniper

/**
 * Copy right of Prasanth Nath.
 * Date: 6/6/11, Time: 4:10 PM
 * Do not use without permission.
 */
class AutoBidState {
    AutoBid autobid
    Sniper sniper

    def handle(Auction auction, Bid bid, AuctionStateListener listener) {
        autobid?.bidHigher bid.price as int, sniper
    }
}
