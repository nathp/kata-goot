package pgoos.sniper

/**
 * Copy right of Prasanth Nath.
 * Date: 5/26/11, Time: 7:37 PM
 * Do not use without permission.
 */
class FakeServer {

    MessageHandler auction

    FakeServer(MessageHandler auction) {
        this.auction = auction
    }

    def sendWelcome(String auctionid) {
        def prefix = prefix()
        auction.handleMessage("$prefix:$auctionid:Welcome")
    }

    String prefix() {
        "BI:1.1"
    }

    def closeAuction(String auctionId, String price, String clientId) {
        def prefix = prefix();
        auction.handleMessage("$prefix:$auctionId:Close:$price:$clientId" as String)

    }

    def mimicBid(String auctionid, String price, String clientid) {
        def prefix = prefix()
        auction.handleMessage("$prefix:$auctionid:Bid:$price:$clientid")
    }


}
