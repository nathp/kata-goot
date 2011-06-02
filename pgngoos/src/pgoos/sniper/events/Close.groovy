package pgoos.sniper.events

import pgoos.sniper.AuctionStateListener
import pgoos.sniper.Auction
import pgoos.sniper.AuctionMessage

/**
 * Copy right of Prasanth Nath.
 * Date: 5/28/11, Time: 6:16 PM
 * Do not use without permission.
 */
class Close extends AuctionEvent {

    String price
    String client

    static Close create(AuctionMessage message) {
        new Close(auctionId: message.id, price:message.column(COLUMNS.price), client: message.column(COLUMNS.clientId))
    }

    static Map COLUMNS = ["price" : 4, "clientId" :  5]

    @Override
    void handle(AuctionStateListener stateListener, Auction auction) {
        if (auction.clientId != client) {
            stateListener.auctionLost this
        } else {
            stateListener.won this
        }
        auction.close()
    }


    boolean equals(o) {
        if (this.is(o)) return true;
        if (getClass() != o.class) return false;

        Close close = (Close) o;

        if (client != close.client) return false;
        if (auctionId != close.auctionId) return false;
        if (price != close.price) return false;

        return true;
    }

    int hashCode() {
        int result;
        result = (price != null ? price.hashCode() : 0);
        result = 31 * result + (client != null ? client.hashCode() : 0);
        result = 31 * result + (auctionId != null ? auctionId.hashCode() : 0);
        return result;
    }
}
