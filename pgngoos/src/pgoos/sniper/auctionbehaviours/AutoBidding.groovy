package pgoos.sniper.auctionbehaviours


import pgoos.sniper.Auction
import pgoos.sniper.AuctionStateListener
import pgoos.sniper.AutoBid
import pgoos.sniper.Sniper
import pgoos.sniper.events.AuctionEvent
import pgoos.sniper.events.Bid

/**
 * Copy right of Prasanth Nath.
 * Date: 6/6/11, Time: 4:10 PM
 * Do not use without permission.
 */
class AutoBidding implements Behaviour{
    AutoBid autobid
    Sniper sniper

    @Override
    Processing handle(Auction auction, AuctionEvent bid, AuctionStateListener listener) {
        if (bid instanceof Bid) {
            if (autobid.exceededStopPrice(bid as Bid)) {
                listener.exceededStopPrice bid.asStopPriceBreached()
            } else {
                autobid?.bidHigher bid.price as int, sniper
            }

        }
        Processing.CONTINUE

    }

    boolean exceededStopPrice(Bid b) {
        autobid?.exceededStopPrice(b)
    }
}
