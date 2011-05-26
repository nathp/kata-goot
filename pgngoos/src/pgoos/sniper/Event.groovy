package pgoos.sniper

/**
 * Copy right of Prasanth Nath.
 * Date: 5/26/11, Time: 4:15 PM
 * Do not use without permission.
 */
class Event {

    String id
    Type type
    Properties properties

    static Event Bid(String itemId, String price, String clientId) {
        new Event(id: itemId, type: Type.Bid, properties: Properties.from("price", price))
    }

    boolean isNewConnection() {
        type == Type.NewAuction
    }

    boolean isBid() {
        type == Type.Bid
    }


    enum Type {
        NewAuction,
        Bid,
        UNKNOWN
    }

    static Event UNKNOWN(String[] tokens) {
        new Event(id: null, type: Type.UNKNOWN)
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
        return super.toString() + "id:$id, event:$type, map:$properties"
    }

    static class Properties {

        def map = new HashMap()

        static Properties from(String key, String value) {
            Properties p = new Properties()
            p.append(key, value)
        }

        def append(String key, String value) {
            map.put(key, value)
            this
        }



        boolean equals(o) {
            if (this.is(o)) return true;
            if (getClass() != o.class) return false;

            Properties that = (Properties) o;

            if (map != that.map) return false;

            return true;
        }

        int hashCode() {
            return (map != null ? map.hashCode() : 0);
        }


        public String toString() {
            return map
        }
    }

}
