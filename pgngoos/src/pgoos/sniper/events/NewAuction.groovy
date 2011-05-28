package pgoos.sniper.events

import pgoos.sniper.Message
import pgoos.sniper.StateListener
import pgoos.sniper.Auction

/**
 * Copy right of Prasanth Nath.
 * Date: 5/28/11, Time: 5:08 PM
 * Do not use without permission.
 */
class NewAuction extends SniperEvent {
    Message message

    static NewAuction create(Message message) {
        new NewAuction(auctionId: message.id, message: message)

    }

    @Override
    void handle(StateListener stateListener, Auction auction) {
        stateListener.connectedNewAuction message
    }
}
