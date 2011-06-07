package pgoos.sniper

import pgoos.sniper.events.Bid
import pgoos.sniper.events.EventFactory
import pgoos.sniper.auctionbehaviours.*

/**
 * Copy right of Prasanth Nath.
 * Date: 5/27/11, Time: 9:18 PM
 * Do not use without permission.
 *
 * Stateful representation of an Auction of some item.
 *
 */
class Auction {

  String auctionId

  String clientId
  AuctionStateListener listener = AuctionStateListener.NONE
  Sniper sniper

  LastBidPriceUpdator lastPrice = new LastBidPriceUpdator()
  ActionChain all = new ActionChain().then(new pgoos.sniper.auctionbehaviours.NewAuction())
      .then(lastPrice).then(new Closing()).then(new Bidding())

  public String toString() {
    auctionId
  }

  def autoBid(AutoBid autoBid, Sniper sniper) {
    all.then(new AutoBidding(sniper: sniper, autobid: autoBid))
  }

  def update(AuctionMessage message) {
    def event = EventFactory.createFrom(message)
    all.do this, event, listener
  }

  boolean isLoosing(Bid bid) {
    isNotOurBid(bid) && !firstBidUpdate() && bid.isMoreThan(lastPrice.price())
  }

  private boolean isNotOurBid(Bid bid) {
    return !bid.belongsToClient(auctionId)
  }

  void close() {
  }

  boolean firstBidUpdate() {
    lastPrice.firstBidUpdate()
  }
}
