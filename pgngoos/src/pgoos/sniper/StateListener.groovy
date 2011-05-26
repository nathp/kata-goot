package pgoos.sniper

/**
 * Copy right of Prasanth Nath.
 * Date: 5/26/11, Time: 4:00 PM
 * Do not use without permission.
 */
interface StateListener {
    void connectedNewAuction(Event event)

    void bidUpdate(Event event)
}
