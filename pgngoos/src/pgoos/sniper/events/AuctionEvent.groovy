package pgoos.sniper.events

import pgoos.sniper.AuctionStateListener
import pgoos.sniper.Auction

/**
 * Copy right of Prasanth Nath.
 * Date: 5/28/11, Time: 5:08 PM
 * Do not use without permission.
 *
 * Rich domain objects that explain what is happening in the Auction process.
 */
public abstract class AuctionEvent {
    String auctionId
    abstract void handle(AuctionStateListener stateListener, Auction auction)


}