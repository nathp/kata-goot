package pgoos.sniper

import pgoos.sniper.events.*
import pgoos.sniper.events.auctionstates.Closing
import pgoos.sniper.events.auctionstates.BidState
import pgoos.sniper.events.auctionstates.AutoBidState

/**
 * Copy right of Prasanth Nath.
 * Date: 5/27/11, Time: 9:18 PM
 * Do not use without permission.
 *
 * Stateful representation of an Auction of some item.
 *
 */
class Auction {

    boolean firstBidUpdate() {
        ourLastBidPrice == null
    }

    String ourLastBidPrice

    AutoBid autobid

    String auctionId
    String clientId
    AuctionStateListener listener = AuctionStateListener.NONE
    Sniper sniper

    public String toString() {
        auctionId
    }

    def autoBid(AutoBid autoBid, Sniper sniper) {
        this.autobid = autoBid
        this.sniper = sniper
    }

    def update(AuctionMessage message) {
        def event = EventFactory.createFrom(message)
        if (event instanceof Bid) {
            new BidState().handle(this, event as Bid, listener)
            new AutoBidState(sniper: sniper, autobid: autobid).handle(this, event as Bid, listener)
        }  else if (event instanceof Close) {
            new Closing().handle(this, event as Close, listener)
        } else if (event instanceof NewAuction) {
            listener.connectedNewAuction event as NewAuction
        }
        postHandle(event)
    }

    private def postHandle(AuctionEvent msg) {
        if (isResponseUpdateForOurOwnBid(msg)) {
            ourLastBidPrice = msg.price
        }
    }

    boolean isResponseUpdateForOurOwnBid(AuctionEvent event) {
        event instanceof Bid && event.client == clientId
    }

    boolean isLoosing(Bid bid) {
        isNotOurBid(bid) && !firstBidUpdate() && bid.isMoreThan(ourLastBidPrice as int)
    }

    private boolean isNotOurBid(Bid bid) {
        return !bid.belongsToClient(auctionId)
    }

    void close() {
    }

    boolean exceededStopPrice(Bid bid) {
        autobid?.exceededStopPrice(bid)
    }

}
