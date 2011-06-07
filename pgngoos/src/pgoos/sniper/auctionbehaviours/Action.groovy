package pgoos.sniper.auctionbehaviours

import pgoos.sniper.Auction
import pgoos.sniper.AuctionStateListener
import pgoos.sniper.events.AuctionEvent

/**
 * Copy right of Prasanth Nath.
 * Date: 6/6/11, Time: 4:24 PM
 * Do not use without permission.
 */
public interface Action {
  Processing do(Auction auction, AuctionEvent event, AuctionStateListener auctionStateListener)
}