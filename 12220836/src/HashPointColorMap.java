import java.awt.*;
import java.util.Arrays;
import java.util.Objects;

// A map that associates a position (objects of type 'Point') with a color (objects of type 'Color').
// The number of key-value pairs is not limited.
// The map is implemented as hash map. The map does not contain any keys or values being 'null'.
public class HashPointColorMap {
    private SimplePointColorMap[] table;

    public HashPointColorMap() {
        table = new SimplePointColorMap[65];
    }

    // Adds a new key-value association to this map. If the key already exists in this map,
    // the value is replaced and the old value is returned. Otherwise, 'null' is returned.
    // Precondition: key != null && value != null.
    public Color put(Point key, Color value) {
        int index = find(key);
        if(index == -1 || table[index] == null) {
            SimplePointColorMap newEntry = new SimplePointColorMap(table.length);
            newEntry.put(key, value);
            table[index] = newEntry;
            return null;
        }

        SimplePointColorMap entry = table[index];
        Color oldValue = entry.get(key);
        entry.put(key, value);
        return oldValue;
    }

    private int find(Point point) {
        if (point == null) {
            return -1;
        }

        return point.hashCode() % (table.length - 1);
    }

    // Returns the value associated with the specified key, i.e. the method returns the color
    // associated with the specified point.
    // More formally, if this map contains a mapping from a key k to a value v such that
    // key.equals(k) == true, then this method returns v.
    // (There can be at most one such mapping.)
    // Returns 'null' if the key is not contained in this map.
    // Precondition: key != null.
    public Color get(Point key) {
        int index = find(key);

        if(index == -1 || table[index] == null) {
            return null;
        }

        return table[index].get(key);
    }

    // Removes the mapping for a key from this map if it is present. More formally, if this map
    // contains a mapping from key k to value v such that key.equals(k) == true,
    // that mapping is removed. (The map can contain at most one such mapping.)
    // Returns the value to which this map previously associated the key, or 'null' if the map
    // contained no mapping for the key.
    // Precondition: key != null.
    public Color remove(Point key) {
        int index = find(key);
        if(index == -1) {
            return null;
        }

        SimplePointColorMap entry = table[index];
        if(entry == null) {
            return null;
        }

        Color oldValue = entry.get(key);
        entry.remove(key);
        return oldValue;
    }

    // Returns a queue with all keys of this map (ordering is not specified).
    public SimplePointQueue keys() {
        SimplePointQueue simplePointQueue = new SimplePointQueue(8);

        for (SimplePointColorMap entry : table) {
            if (entry != null) {
                SimplePointQueue keys = entry.keys();
                while(keys.size() > 0) {
                    simplePointQueue.add(keys.poll());
                }
            }
        }

        return simplePointQueue;
    }

    // Returns 'true' if the specified key is contained in this map.
    // Returns 'false' otherwise.
    public boolean containsKey(Point key) {
        return get(key) != null;
    }

    // Returns 'true' if the specified value is contained at least once in this map.
    // Returns 'false' otherwise.
    public boolean containsValue(Color value) {
        for (SimplePointColorMap entry : table) {
            if (entry != null) {
                SimplePointQueue keys = entry.keys();
                while(keys.size() > 0) {
                    Color color = entry.get(keys.poll());

                    if(value.equals(color)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    // Returns a string representation of this map with key-value pairs in parentheses, separated
    // by commas (order is not specified).
    // Example: {([9, 2], java.awt.Color[r=255,g=255,b=0]), ([7, 1], java.awt.Color[r=255,g=0,b=0])}
    public String toString() {
        String output = "{";
        for (SimplePointColorMap entry : table) {
            if (entry == null) {
                continue;
            }

            SimplePointQueue keys = entry.keys();
            while(keys.size() > 0) {
                Point key = keys.poll();
                Color color = entry.get(key);
                output += "(" + key.toString() + ", " + color.toString() + "), ";
            }
        }

        if(output.length() < 2) {
            return "";
        }
        output = output.substring(0, output.length() - 2) + "}";
        return output;
    }

    // Returns 'true' if 'this' and 'o' are equal, meaning 'o' is of class 'HashPointColorMap'
    // and 'this' and 'o' contain the same key-value pairs, i.e. the number of key-value pairs is
    // the same in both maps and every key-value pair in 'this' equals one key-value pair in 'o'.
    // Two key-value pairs are equal if the two keys are equal and the two values are equal.
    // Otherwise, 'false' is returned.
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        HashPointColorMap that = (HashPointColorMap) o;
        return Arrays.equals(table, that.table);
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(table);
    }
}

