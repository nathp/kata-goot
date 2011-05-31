package pgoos.sniper

import pgoos.sniper.events.*

/**
 * Copy right of Prasanth Nath.
 * Date: 5/27/11, Time: 9:18 PM
 * Do not use without permission.
 */
class Auction {

    String auctionId
    String clientId

    String ourLastBidPrice

    public String toString() {
        auctionId
    }

    boolean isLoosing(String price) {
        if (!ourLastBidPrice) {
            return false
        }
        (ourLastBidPrice as int) < (price as int)
    }

    interface State {
        void handle(SniperEvent msg)
    }

    AuctionStateListener listener

    State WAIT_FOR_WELCOME = {SniperEvent msg ->
        msg.handle(listener, delegate)
        switchTo(HANDLE_BID)

    } as State

    State HANDLE_BID = { SniperEvent msg ->
        if (isOurUpdate(msg)) {
            ourLastBidPrice = msg.price
        }
        msg.handle(listener, delegate)
    } as State

    boolean isOurUpdate(Bid event) {
        println "${event.client}, $clientId"
        event.client == clientId
    }

    State currentState = WAIT_FOR_WELCOME

    private def switchTo(State state) {
        currentState = state
    }

    def update(Message message) {
        currentState.handle(Events.createFrom(message))
    }

}
