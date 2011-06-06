package pgoos.sniper.auctionbehaviours

import pgoos.sniper.Auction
import pgoos.sniper.AuctionStateListener
import pgoos.sniper.events.AuctionEvent

/**
 * Copy right of Prasanth Nath.
 * Date: 6/6/11, Time: 5:47 PM
 * Do not use without permission.
 */
class ChainedBehaviour implements Behaviour {

  def delegates = []

  def then(Behaviour next) {
    delegates << next
    this
  }

  @Override
  Processing handle(Auction auction, AuctionEvent event, AuctionStateListener listener) {
    for (b in delegates) {
      def action = b.handle(auction, event, listener)
      if (action == Processing.STOP) {
        break
      }
    }
    return Processing.STOP
  }
}
