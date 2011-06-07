package pgoos.sniper.auctionbehaviours

import pgoos.sniper.Auction
import pgoos.sniper.AuctionStateListener
import pgoos.sniper.events.AuctionEvent

/**
 * Copy right of Prasanth Nath.
 * Date: 6/6/11, Time: 5:47 PM
 * Do not use without permission.
 */
class ActionChain implements Action {

  def delegates = []

  def then(Action next) {
    delegates << next
    this
  }

  @Override
  Processing do(Auction auction, AuctionEvent event, AuctionStateListener listener) {
    for (b in delegates) {
      def action = b.do(auction, event, listener)
      if (action == Processing.STOP) {
        break
      }
    }
    return Processing.STOP
  }
}
