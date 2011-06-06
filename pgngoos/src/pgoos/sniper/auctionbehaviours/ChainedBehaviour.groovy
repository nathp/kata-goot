package pgoos.sniper.auctionbehaviours

import pgoos.sniper.Auction
import pgoos.sniper.AuctionStateListener
import pgoos.sniper.events.AuctionEvent
import pgoos.sniper.auctionbehaviours.Chain.Processing

/**
 * Copy right of Prasanth Nath.
 * Date: 6/6/11, Time: 5:47 PM
 * Do not use without permission.
 */
class ChainedBehaviour implements Behaviour {

  def delegates = []

  @Override
  void handle(Auction auction, AuctionStateListener listener, AuctionEvent event) {
    for (b in delegates) {
      def action = b.doHandle(auction, event, listener)
      if (action == Processing.STOP) {
        break
      }
    }
  }

  def then(Behaviour next) {
    delegates << next
    this
  }
}
