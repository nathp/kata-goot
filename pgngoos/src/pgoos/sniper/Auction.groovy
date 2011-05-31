package pgoos.sniper

import pgoos.sniper.events.*

/**
 * Copy right of Prasanth Nath.
 * Date: 5/27/11, Time: 9:18 PM
 * Do not use without permission.
 */
class Auction {

    String id
    String clientid

    public String toString() {
        id
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
        msg.handle(listener, delegate)
    } as State

    State currentState = WAIT_FOR_WELCOME

    private def switchTo(State state) {
        currentState = state
    }

    def update(Message message) {
        currentState.handle(Events.createFrom(message))
    }

}
