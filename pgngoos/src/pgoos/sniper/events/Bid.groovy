package pgoos.sniper.events

import pgoos.sniper.AuctionMessage

/**
 * Copy right of Prasanth Nath.
 * Date: 5/28/11, Time: 5:09 PM
 * Do not use without permission.
 */
class Bid extends AuctionEvent {

    final String price
    final String client
    final State state

    static Bid create(AuctionMessage message) {
        new Bid(message.id, message.column(COLUMNS.price), message.column(COLUMNS.client), State.NotKnown)
    }

    static Map COLUMNS = ["price": 4, "client": 5]

    Bid(String auctionId, String price, String client, State state) {
        this.auctionId = auctionId
        this.price = price
        this.client = client
        this.state = state
    }

    static Bid newBid(String id, String price, String client) {
        new Bid(id, price, client, State.NotKnown)
    }

    Bid asStopPriceBreached() {
        new Bid(auctionId, price, client, State.StopPriceBreached)
    }

    Bid winning() { new Bid(auctionId, price, client, State.Winning) }

    Bid loosing() { new Bid(auctionId, price, client, State.Losing) }

    boolean isMoreThan(int price) { (this.price as int) > price }

    boolean belongsToClient(String ourClientId) { client == ourClientId }

    public String toString() {
        return "Bid{" +
                "price='" + price + '\'' +
                ", auction ='" + auctionId + '\'' +
                ", client='" + client + '\'' +
                ", state=" + state +
                '}';
    }

    static enum State {
        Winning, Losing, Lost, Won, NotKnown, StopPriceBreached
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
