package pgoos.sniper.auctionbehaviours

import pgoos.sniper.Auction
import pgoos.sniper.AuctionStateListener
import pgoos.sniper.events.AuctionEvent
import pgoos.sniper.events.Close

/**
 * Copy right of Prasanth Nath.
 * Date: 6/6/11, Time: 4:06 PM
 * Do not use without permission.
 */
class Closing implements Behaviour {
  @Override
  Processing handle(Auction auction, AuctionEvent close, AuctionStateListener listener) {
    def shouldHandle = close instanceof Close
    if (shouldHandle) {
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
