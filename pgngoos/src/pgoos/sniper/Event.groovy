package pgoos.sniper

/**
 * Copy right of Prasanth Nath.
 * Date: 5/26/11, Time: 4:15 PM
 * Do not use without permission.
 */
class Event {

    String id

    static Event Welcome(String s) {
        new Event(id:s)
    }
}
