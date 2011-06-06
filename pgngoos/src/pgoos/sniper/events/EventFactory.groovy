package pgoos.sniper.events

import pgoos.sniper.AuctionMessage

/**
 * Copy right of Prasanth Nath.
 * Date: 5/28/11, Time: 5:52 PM
 * Do not use without permission.
 */
class EventFactory {
  static AuctionEvent createFrom(AuctionMessage message) {
    switch (message.type) {
      case "Welcome": return NewAuction.create(message);
      case "Bid": return Bid.create(message);
      case "Close": return Close.create(message);
    }
  }

}
