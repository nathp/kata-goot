package pgoos.sniper

import pgoos.sniper.events.Bid

/**
 * Copy right of Prasanth Nath.
 * Date: 5/26/11, Time: 4:00 PM
 * Do not use without permission.
 */
interface AuctionStateListener {
    AuctionStateListener NONE = {} as AuctionStateListener

    void connectedNewAuction(Message event)

    void bidUpdate(Message event)

    void auctionLost(Message message)

    void won(Message message)

    void loosing(Bid bid)
}
