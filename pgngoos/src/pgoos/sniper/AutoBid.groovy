package pgoos.sniper

import pgoos.sniper.events.Bid

/**
 * Copy right of Prasanth Nath.
 * Date: 6/6/11, Time: 11:59 AM
 * Do not use without permission.
 */
class AutoBid {
  String item
  int increment
  int stopPrice

  static AutoBid forItem(String item) {
    new AutoBid(item: item)
  }

  AutoBid increment(int increment) {
    this.increment = increment
    this
  }

  AutoBid stopAt(int stopPrice) {
    this.stopPrice = stopPrice
    this
  }

  void bidHigher(int price, Sniper sniper) {
    def nextBid = nextPrice(price)
    if (nextBid < stopPrice) {
      sniper.bid item, nextBid + ""
    }
  }

  private int nextPrice(int price) {
    return price + increment
  }


  boolean exceededStopPrice(Bid bid) {
    def nextBid = nextPrice(bid.price as int)
    nextBid >= stopPrice
  }
}
