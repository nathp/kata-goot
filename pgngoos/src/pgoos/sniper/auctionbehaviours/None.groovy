package pgoos.sniper.auctionbehaviours

import pgoos.sniper.Auction
import pgoos.sniper.AuctionStateListener
import pgoos.sniper.events.AuctionEvent

/**
 * Copy right of Prasanth Nath.
 * Date: 6/6/11, Time: 5:07 PM
 * Do not use without permission.
 */
class None implements  Behaviour {

  @Override
  Processing handle(Auction auction, AuctionEvent event, AuctionStateListener auctionStateListener) {
    return null  //To change body of implemented methods use File | Settings | File Templates.
  }
}
