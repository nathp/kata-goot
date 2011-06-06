package pgoos.sniper

/**
 * Copy right of Prasanth Nath.
 * Date: 5/26/11, Time: 4:15 PM
 * Do not use without permission.
 */
/**
 * Low level message. A thin wrapper around text coming over the wire
 */
class AuctionMessage {

  String id
  String type
  String props

  static AuctionMessage parseFrom(String rawMessage) {
    new MessageParser().parse rawMessage
  }

  static AuctionMessage createFrom(String auctionId, String type, String properties) {
    if (properties.startsWith(":")) {
      properties = properties.substring(1)
    }
    new AuctionMessage(id: auctionId, type: type, props: properties)
  }

  static String rawBidMessage(String auctionId, String price, String clientId) {
    "B1:1.1:$auctionId:Bid:$price:$clientId"
  }

  String column(int c) {
    int correctedCol = c - 4    // B1, 1.1, auctionId, Command-name are removed
    def items = props.split(":").collect { it.trim() }
    correctedCol < items.size() ? items[correctedCol] : null
  }

  def valueOf(def key) { properties.get(key)}

  boolean equals(o) {
    if (this.is(o)) return true;
    if (getClass() != o.class) return false;

    AuctionMessage message = (AuctionMessage) o;

    if (id != message.id) return false;
    if (props != message.props) return false;
    if (type != message.type) return false;

    return true;
  }

  int hashCode() {
    int result;
    result = (id != null ? id.hashCode() : 0);
    result = 31 * result + (type != null ? type.hashCode() : 0);
    result = 31 * result + (props != null ? props.hashCode() : 0);
    return result;
  }

  @Override
  String toString() {
    return super.toString() + " auctionId:$id, event:$type, properties[$props]"
  }


}
