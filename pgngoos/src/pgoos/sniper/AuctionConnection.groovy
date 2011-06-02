package pgoos.sniper

/**
 * Copy right of Prasanth Nath.
 * Date: 5/30/11, Time: 8:27 PM
 * Do not use without permission.
 */
interface AuctionConnection {
    static AuctionConnection NONE = {} as AuctionConnection

    void sendMessage(String message)
}
