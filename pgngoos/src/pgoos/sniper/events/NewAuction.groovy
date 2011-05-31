package pgoos.sniper.events

import pgoos.sniper.Message
import pgoos.sniper.AuctionStateListener
import pgoos.sniper.Auction

/**
 * Copy right of Prasanth Nath.
 * Date: 5/28/11, Time: 5:08 PM
 * Do not use without permission.
 */
class NewAuction extends AuctionEvent {

    static NewAuction create(Message message) {
        new NewAuction(auctionId: message.id)

    }

    @Override
    void handle(AuctionStateListener stateListener, Auction auction) {
        stateListener.connectedNewAuction this
    }

    boolean equals(o) {
        if (this.is(o)) return true;
        if (getClass() != o.class) return false;

        NewAuction that = (NewAuction) o;

        if (auctionId != that.auctionId) return false;

        return true;
    }

    int hashCode() {
        return (auctionId != null ? auctionId.hashCode() : 0);
    }
}
