package pgoos.sniper

/**
 * Copy right of Prasanth Nath.
 * Date: 5/26/11, Time: 4:00 PM
 * Do not use without permission.
 */
class Sniper implements MessageHandler {

    StateListener listener
    String id

    def start() {
        // connect to server
    }

    @Override def handleMessage(String message) {
        def msg = new MessageParser().parse(message)
        if (msg.isNewConnection())
            listener.connectedNewAuction msg
        else if (msg.isBid())
            listener.bidUpdate msg
    }
}
