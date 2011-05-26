package pgoos.sniper

/**
 * Copy right of Prasanth Nath.
 * Date: 5/26/11, Time: 4:00 PM
 * Do not use without permission.
 */
class Sniper implements  EventListener {
    Auction auction
    StateListener listener
    String id

    def start() {
        auction.register()
    }

    @Override
    void handleEvent(Event e) {
        if (e.isNewConnection())
            listener.connectedNewAuction e
        else if (e.isBid())
            listener.bidUpdate e

    }
}
