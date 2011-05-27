package pgoos.sniper;

/**
 * Copy right of Prasanth Nath.
 * Date: 5/27/11, Time: 8:46 PM
 * Do not use without permission.
 */
public class Properties {


    final static Properties NONE = new Properties()

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

    def valueOf(def key) {
        map.get(key)
    }
}


