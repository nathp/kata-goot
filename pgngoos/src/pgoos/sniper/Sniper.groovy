package pgoos.sniper

/**
 * Copy right of Prasanth Nath.
 * Date: 5/26/11, Time: 4:00 PM
 * Do not use without permission.
 */
class Sniper {
    Auction auction
    StateListener listener

    def start() {
        Event welcome = auction.register()
        listener.connectedNewAuction welcome.id
    }
}
