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
        Bid bid = new Bid(price:message.column(3), client: message.column(4), message:message)
        bid
    }

    void handle(AuctionStateListener stateListener, Auction auction) {
        stateListener.bidUpdate message
    }

    enum State {
        Winning,
        Losing,
        Lost,
        Won,
        NotKnown
    }

}
