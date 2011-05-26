package pgoos.sniper

/**
 * Copy right of Prasanth Nath.
 * Date: 5/26/11, Time: 4:15 PM
 * Do not use without permission.
 */
class Event {

    String id
    Type type


    enum Type {
        NewAuction,
        UNKNOWN
    }

    static Event UNKNOWN(String[] tokens) {
        new Event(id:null, type:Type.UNKOWN)
    }

    static Event Welcome(String s) {
        new Event(id: s)
    }

    static Event newAuction(String s) {
        new Event(id: s, type: Type.NewAuction)
    }


    boolean equals(o) {
        if (this.is(o)) return true;
        if (getClass() != o.class) return false;

        Event event = (Event) o;

        if (id != event.id) return false;
        if (type != event.type) return false;

        return true;
    }

    int hashCode() {
        int result;
        result = (id != null ? id.hashCode() : 0);
        result = 31 * result + (type != null ? type.hashCode() : 0);
        return result;
    }

    @Override
    String toString() {
        return super.toString() + "id:$id, event:$type"
    }


}
