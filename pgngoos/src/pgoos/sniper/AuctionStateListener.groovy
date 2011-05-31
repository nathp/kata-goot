package pgoos.sniper

import pgoos.sniper.events.Bid
import pgoos.sniper.events.NewAuction

/**
 * Copy right of Prasanth Nath.
 * Date: 5/26/11, Time: 4:00 PM
 * Do not use without permission.
 */
interface AuctionStateListener {
    AuctionStateListener NONE = {} as AuctionStateListener

    void connectedNewAuction(NewAuction event)

    void bidUpdate(Bid bid)

    void auctionLost(Message message)

    void won(Message message)

    void loosing(Bid bid)
}
