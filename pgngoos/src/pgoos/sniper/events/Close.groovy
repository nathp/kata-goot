package pgoos.sniper.events

import pgoos.sniper.StateListener
import pgoos.sniper.Auction
import pgoos.sniper.Message

/**
 * Copy right of Prasanth Nath.
 * Date: 5/28/11, Time: 6:16 PM
 * Do not use without permission.
 */
class Close extends SniperEvent {
    Message message

    static Close create(Message message) {
        new Close(auctionId: message.id, message:message)
    }

    @Override
    void handle(StateListener stateListener, Auction auction) {
        if (auction.id != property("clientId")) {
            stateListener.auctionLost message
        }
    }

    def property(String key) {
        message.valueOf key
    }
}
