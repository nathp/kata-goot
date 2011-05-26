package pgoos.sniper

/**
 * Copy right of Prasanth Nath.
 * Date: 5/26/11, Time: 7:37 PM
 * Do not use without permission.
 */
class FakeServer {
    Auction auction

    FakeServer(Auction auction) {
        this.auction = auction
    }

    def sendWelcome(String auctionid) {
        auction.handleMessage("BI:1.1:$auctionid:Welcome")
    }
}
