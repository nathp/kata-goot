package pgoos.sniper.events

import pgoos.sniper.Message
import pgoos.sniper.StateListener
import pgoos.sniper.Auction

/**
 * Copy right of Prasanth Nath.
 * Date: 5/28/11, Time: 5:08 PM
 * Do not use without permission.
 */
public abstract class SniperEvent {
    String auctionId
    abstract void handle(StateListener stateListener, Auction auction)
}