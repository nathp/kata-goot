package pgoos.sniper

/**
 * Copy right of Prasanth Nath.
 * Date: 5/26/11, Time: 4:00 PM
 * Do not use without permission.
 */
class Sniper implements AuctionMessageHandler {

    AuctionStateListener listener
    String id
    Auctions auctions
    AuctionConnection server

    def start() {
        // connect to server
        auctions = new Auctions(listener:listener, clientId: id)
    }

    @Override void handleMessage(String message) {

        String auctionId = new MessageParser().parseId(message)
        Auction a = auctions.findAuctionWithId(auctionId)
        a.update(new MessageParser().parse(message))

    }

    def bid(String auctionId, String price) {
        server.sendMessage("B1:1.1:$auctionId:Bid:$price:$id")
    }
}
