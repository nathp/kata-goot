package pgoos.sniper

/**
 * Copy right of Prasanth Nath.
 * Date: 5/26/11, Time: 4:00 PM
 * Do not use without permission.
 */
class Sniper implements MessageHandler {

    StateListener listener
    String id
    Auctions auctions

    def start() {
        // connect to server
        auctions = new Auctions(listener:listener)
    }

    @Override void handleMessage(String message) {

        String auctionId = new MessageParser().parseId(message)
        Auction a = auctions.autionFor(auctionId)
        a.update(new MessageParser().parse(message))

    }
}
