package pgoos.sniper

/**
 * Copy right of Prasanth Nath.
 * Date: 5/26/11, Time: 7:51 PM
 * Do not use without permission.
 */
class MessageParser {
    Message parse(String message) {
        def tokens = message.split(":")

        def serverid = serverid(tokens)
        def type = type(tokens)

        if ("Welcome" == type) {
            return Message.newAuction(serverid)
        }
        if ("Bid" == type) {
            return Message.Bid(serverid, Properties.from("price", tokens[4]))
        }
        throw new RuntimeException("Unknown message type $message")
    }

    def type(String[] tokens) {
        tokens[3]
    }

    def serverid(String[] tokens) {
        tokens[2]
    }
}
