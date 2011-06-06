package pgoos.sniper.auctionbehaviours

import pgoos.sniper.Auction
import pgoos.sniper.AuctionStateListener
import pgoos.sniper.events.AuctionEvent

/**
 * Copy right of Prasanth Nath.
 * Date: 6/6/11, Time: 4:25 PM
 * Do not use without permission.
 */
abstract class Chain implements Behaviour {

    Behaviour next = new None()

    enum Processing{ STOP, CONTINUE }

    @Override
    void handle(Auction auction, AuctionStateListener listener, AuctionEvent event) {
        def action = doHandle(auction, event, listener)
        if (action == Processing.CONTINUE)  {
            next.handle(auction, listener, event)
        }
    }

    abstract Processing doHandle(Auction auction, AuctionEvent event, AuctionStateListener auctionStateListener)
}
