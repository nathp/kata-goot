package pgoos.sniper.events

import pgoos.sniper.Message
import pgoos.sniper.AuctionStateListener
import pgoos.sniper.Auction

/**
 * Copy right of Prasanth Nath.
 * Date: 5/28/11, Time: 5:09 PM
 * Do not use without permission.
 */
class Bid extends SniperEvent {

    String price
    String client
    State state = State.NotKnown
    Message message

    static Bid create(Message message) {
        Bid bid = new Bid(auctionId: message.id, price:message.column(4), client: message.column(5), message:message)
        bid
    }

    void handle(AuctionStateListener stateListener, Auction auction) {
        if (auction.isLoosing(price)) {
            updateState(State.Losing)
            stateListener.loosing this
        } else
            stateListener.bidUpdate message
    }

    def updateState(State state) {
        this.state = state
    }

    enum State {
        Winning,
        Losing,
        Lost,
        Won,
        NotKnown
    }


    boolean equals(o) {
        if (this.is(o)) return true;
        if (getClass() != o.class) return false;

        Bid bid = (Bid) o;

        if (client != bid.client) return false;
        if (price != bid.price) return false;
        if (state != bid.state) return false;

        return true;
    }

    int hashCode() {
        int result;
        result = (price != null ? price.hashCode() : 0);
        result = 31 * result + (client != null ? client.hashCode() : 0);
        result = 31 * result + (state != null ? state.hashCode() : 0);
        return result;
    }
}
