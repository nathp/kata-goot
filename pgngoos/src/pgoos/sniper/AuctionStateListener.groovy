package pgoos.sniper

import pgoos.sniper.events.Bid
import pgoos.sniper.events.NewAuction
import pgoos.sniper.events.Close

/**
 * Copy right of Prasanth Nath.
 * Date: 5/26/11, Time: 4:00 PM
 * Do not use without permission.
 *
 * Listener to keep an eye on what is happening in the doman model.
 * Can be used to populate stuff in UI, for egs.
 *
 */
interface AuctionStateListener {
    AuctionStateListener NONE = {} as AuctionStateListener

    void connectedNewAuction(NewAuction event)

    void bidUpdate(Bid bid)

    void auctionLost(Close close)

    void won(Close close)

    void loosing(Bid bid)
}
