package pgoos.sniper

/**
 * Copy right of Prasanth Nath.
 * Date: 5/26/11, Time: 4:00 PM
 * Do not use without permission.
 */
class Sniper implements AuctionMessageHandler {

    String clientId
    Auctions auctions
    AuctionConnection auctionConnection = AuctionConnection.NONE

    def start() {
        // connect via auctionConnection
    }

    @Override void handleMessage(String message) {
        def msg = AuctionMessage.parseFrom(message)
        Auction a = auctions.findAuctionWithId(msg.id)
        a.update(msg)
    }

    def bid(String auctionId, String price) {
        auctionConnection.sendMessage AuctionMessage.rawBidMessage(auctionId, price, clientId)
    }
}
