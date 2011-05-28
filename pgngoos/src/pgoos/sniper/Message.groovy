package pgoos.sniper

/**
 * Copy right of Prasanth Nath.
 * Date: 5/26/11, Time: 4:15 PM
 * Do not use without permission.
 */
class Message {

    String id
    String type
    String props

    static Message from(String rawMessage) {
        new MessageParser().parse rawMessage
    }

    static Message renameThis(String auctionId, String type, String properties) {
        if (properties.startsWith(":")) {
            properties = properties.substring(1)
        }
        new Message(id:auctionId, type:type, props:properties)
    }

    String column(int c) {
        int correctedCol = c - 4


        println props.split(":")
        props.split(":")[correctedCol]
    }

    def valueOf(def key) { properties.get(key)}

    boolean equals(o) {
        if (this.is(o)) return true;
        if (getClass() != o.class) return false;

        Message message = (Message) o;

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
        return super.toString() + " id:$id, event:$type, map:$props"
    }



}
