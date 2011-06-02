package pgoos.sniper

import pgoos.sniper.events.*

/**
 * Copy right of Prasanth Nath.
 * Date: 5/27/11, Time: 9:18 PM
 * Do not use without permission.
 *
 * Represents the stateful representation of an Auction for some item.
 *
 */
class Auction {

    String auctionId
    String clientId

    AuctionStateListener listener

    String ourLastBidPrice  = null

    public String toString() {
        auctionId
    }

    boolean isLoosing(Bid bid) {
        !bid.isOurs(auctionId) && !firstBidUpdate() && bid.isMoreThan(ourLastBidPrice as int)
    }

    boolean firstBidUpdate() {
        ourLastBidPrice == null
    }

    private interface State {
        void handle(AuctionEvent msg)
    }

    State WAIT_FOR_WELCOME = {AuctionEvent msg ->
        msg.handle(listener, delegate)
        switchTo(HANDLE_BID)

    } as State

    State HANDLE_BID = { AuctionEvent msg ->
        msg.handle(listener, delegate)
        if (isResponseUpdateForOurOwnBid(msg)) {
            ourLastBidPrice = msg.price
        }
    } as State

    State CLOSED = { AuctionEvent e ->
    } as State

    boolean isResponseUpdateForOurOwnBid(AuctionEvent event) {
        event instanceof Bid && event.client == clientId
    }

    State currentState = WAIT_FOR_WELCOME

    private def switchTo(State state) {
        currentState = state
    }

    def update(AuctionMessage message) {
        currentState.handle(Events.createFrom(message))
    }

    void close() {
        switchTo CLOSED
    }
}
