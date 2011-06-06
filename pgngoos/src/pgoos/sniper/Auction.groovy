package pgoos.sniper

import pgoos.sniper.events.*
import pgoos.sniper.auctionbehaviours.Closing
import pgoos.sniper.auctionbehaviours.Bidding
import pgoos.sniper.auctionbehaviours.AutoBidding
import pgoos.sniper.auctionbehaviours.Behaviour
import pgoos.sniper.auctionbehaviours.LastBidPriceUpdator
import pgoos.sniper.auctionbehaviours.ChainedBehaviour

/**
 * Copy right of Prasanth Nath.
 * Date: 5/27/11, Time: 9:18 PM
 * Do not use without permission.
 *
 * Stateful representation of an Auction of some item.
 *
 */
class Auction {

    String auctionId

    String clientId
    AuctionStateListener listener = AuctionStateListener.NONE
    Sniper sniper

    Behaviour head = new pgoos.sniper.auctionbehaviours.NewAuction()
    LastBidPriceUpdator lastPrice = new LastBidPriceUpdator()
    ChainedBehaviour all = new ChainedBehaviour().then(head).then(lastPrice).then(new Closing()).then(new Bidding())

    public String toString() {
        auctionId
    }

    def autoBid(AutoBid autoBid, Sniper sniper) {
        all.then(new AutoBidding(sniper: sniper, autobid: autoBid))
    }

    def update(AuctionMessage message) {
        def event = EventFactory.createFrom(message)
        all.handle this, listener, event
    }

    boolean isLoosing(Bid bid) {
        isNotOurBid(bid) && !firstBidUpdate() && bid.isMoreThan(lastPrice.price())
    }

    private boolean isNotOurBid(Bid bid) {
        return !bid.belongsToClient(auctionId)
    }

    void close() {
    }

    boolean firstBidUpdate() {
        lastPrice.firstBidUpdate()
    }
}
