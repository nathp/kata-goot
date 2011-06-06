package pgoos.sniper.auctionbehaviours

import pgoos.sniper.Auction
import pgoos.sniper.AuctionStateListener
import pgoos.sniper.events.AuctionEvent
import pgoos.sniper.events.Bid

/**
 * Copy right of Prasanth Nath.
 * Date: 6/6/11, Time: 4:08 PM
 * Do not use without permission.
 */
class Bidding implements Behaviour {

  @Override
  Processing handle(Auction auction, AuctionEvent bid, AuctionStateListener listener) {
    def shouldHandle = bid instanceof Bid
    if (shouldHandle) {
      if (auction.firstBidUpdate()) {
        listener.bidUpdate bid
      } else if (auction.isLoosing(bid)) {
        listener.loosing bid.loosing()
      }
      else {
        listener.bidUpdate bid.winning()
      }
    }
    Processing.CONTINUE
  }
}
