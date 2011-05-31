package pgoos.sniper.events

import pgoos.sniper.Message
import pgoos.sniper.AuctionStateListener
import pgoos.sniper.Auction

/**
 * Copy right of Prasanth Nath.
 * Date: 5/28/11, Time: 5:09 PM
 * Do not use without permission.
 */
class Bid extends AuctionEvent {

    String price
    String client
    final State state

    static Bid create(Message message) {
        new Bid(message.id, message.column(4), message.column(5), State.NotKnown)
    }

    Bid(String auctionId, String price, String client, State state) {
        this.auctionId = auctionId
        this.price = price
        this.client = client
        this.state = state
    }

    static Bid newBid(String id, String price, String client) {
        new Bid(id, price, client, State.NotKnown)
    }

    void handle(AuctionStateListener stateListener, Auction auction) {
        if (auction.firstBidUpdate()) {
            stateListener.bidUpdate this
        } else if (auction.isLoosing(this)) {
            stateListener.loosing this.loosing()
        } else {
            stateListener.bidUpdate this.winning()
        }
    }

    Bid winning() {
        new Bid(auctionId, price, client, State.Winning)
    }

    Bid loosing() {
        new Bid(auctionId, price, client, State.Losing)
    }

    boolean isMoreThan(int price) {
        (this.price as int) > price
    }

    boolean isOurs(String ourClientId) {
        client == ourClientId
    }

    public String toString() {
        return "Bid{" +
                "price='" + price + '\'' +
                ", client='" + client + '\'' +
                ", state=" + state +
                '}';
    }

    static enum State {
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
        if (auctionId != bid.auctionId) return false;
        if (state != bid.state) return false;

        return true;
    }

    int hashCode() {
        int result;
        result = (price != null ? price.hashCode() : 0);
        result = 31 * result + (client != null ? client.hashCode() : 0);
        result = 31 * result + (auctionId != null ? auctionId.hashCode() : 0);
        result = 31 * result + (state != null ? state.hashCode() : 0);
        return result;
    }
}
