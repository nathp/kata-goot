package pgoos.sniper.events

import pgoos.sniper.AuctionStateListener
import pgoos.sniper.Auction

/**
 * Copy right of Prasanth Nath.
 * Date: 5/28/11, Time: 5:08 PM
 * Do not use without permission.
 */
public abstract class AuctionEvent {
    protected String auctionId
    abstract void handle(AuctionStateListener stateListener, Auction auction)


}