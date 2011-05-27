package pgoos.sniper

/**
 * Copy right of Prasanth Nath.
 * Date: 5/27/11, Time: 9:18 PM
 * Do not use without permission.
 */
class Auction {

    String id

    public String toString() {
        id
    }

    interface State {
        void handle(Message msg)
    }

    StateListener listener

    State WAIT_FOR_WELCOME = {Message msg ->
        if (msg.isNewConnection()) {
            listener.connectedNewAuction msg
            switchTo(HANDLE_BID)
        }

    } as State

    State HANDLE_BID = { Message msg ->
        listener.bidUpdate(msg)
    } as State

    State current = WAIT_FOR_WELCOME

    private def switchTo(State state) {
        current = state
    }

    def update(Message message) {
        current.handle(message)
    }

}
