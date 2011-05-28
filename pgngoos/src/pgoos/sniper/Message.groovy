package pgoos.sniper

/**
 * Copy right of Prasanth Nath.
 * Date: 5/26/11, Time: 4:15 PM
 * Do not use without permission.
 */
class Message {

    String id
    Type type
    Properties properties

    static Message Bid(String itemId, Properties properties) {
        new Message(id: itemId, type: Type.Bid, properties: properties)
    }

    static Message AuctionLost(String auctionId, Properties properties) {
        new Message(id: auctionId, type: Type.Lost, properties: properties)
    }

    static Message Closed(String id, Properties properties) {
        new Message(id: id, type: Type.Close, properties: properties)
    }

    static Message from(String rawMessage) {
        new MessageParser().parse rawMessage
    }


    boolean isNewConnection() {
        type == Type.NewAuction
    }

    boolean isBid() {
        type == Type.Bid
    }

    def valueOf(def key) { properties.valueOf(key)}

    boolean isLose(String auctionId) {
        type == Type.Close && valueOf("clientId") != auctionId
    }

    Message asLost() {
        new Message(id:id, type:Type.Lost, properties:properties)
    }

    enum Type {
        NewAuction,
        Bid,
        Lost,
        Close,
        UNKNOWN
    }

    static Message UNKNOWN(String[] tokens) {
        new Message(id: "unknown", type: Type.UNKNOWN, properties:Properties.NONE)
    }

    static Message newAuction(String s) {
        new Message(id: s, type: Type.NewAuction, properties:Properties.NONE)
    }

    boolean equals(o) {
        if (this.is(o)) return true;
        if (getClass() != o.class) return false;

        Message event = (Message) o;

        if (id != event.id) return false;
        if (properties != event.properties) return false;
        if (type != event.type) return false;

        return true;
    }

    int hashCode() {
        int result;
        result = (id != null ? id.hashCode() : 0);
        result = 31 * result + (type != null ? type.hashCode() : 0);
        result = 31 * result + (properties != null ? properties.hashCode() : 0);
        return result;
    }


    @Override
    String toString() {
        return super.toString() + " id:$id, event:$type, map:$properties"
    }


}
