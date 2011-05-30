package pgoos.sniper

/**
 * Copy right of Prasanth Nath.
 * Date: 5/26/11, Time: 4:00 PM
 * Do not use without permission.
 */
interface StateListener {
    StateListener NONE = {} as StateListener

    void connectedNewAuction(Message event)

    void bidUpdate(Message event)

    void auctionLost(Message message)

    void won(Message message)
}
