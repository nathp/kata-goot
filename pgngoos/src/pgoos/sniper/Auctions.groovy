package pgoos.sniper

/**
 * Copy right of Prasanth Nath.
 * Date: 5/27/11, Time: 9:18 PM
 * Do not use without permission.
 */
class Auctions {
    Map map = new HashMap()
    AuctionStateListener listener = AuctionStateListener.NONE
    String clientId

    Auction findAuctionWithId(String id) {
        Auction a = map.get(id) as Auction
        if (!a) {
            a = new Auction(id:id, listener: listener, clientid:clientId)
            map.put(id, a)
        }
        a
    }
}
