package pgoos.sniper

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
}
