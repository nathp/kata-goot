package pgoos.sniper

/**
 * Copy right of Prasanth Nath.
 * Date: 5/26/11, Time: 7:33 PM
 * Do not use without permission.
 */
class DefaultAuction implements Auction {

    EventListener eventListener

    @Override
    Event register() {

    }


    @Override def handleMessage(String message) {
        eventListener.handleEvent(new EventParser().parse(message))
    }

    @Override def registerEventListener(EventListener eventListener) {
        this.eventListener = eventListener
    }
}
