import java.awt.*;
import java.util.Arrays;
import java.util.Objects;

// A map that associates a position with a color. The number of key-value pairs is not limited.
// The map does not contain any keys or values being 'null'.
//
public class SimplePointColorMap {

    private Color[] values;
    private Point[] keys;
    private int top;

    // Initializes this map with an initial capacity (length of internal array).
    // Precondition: initialCapacity > 0.
    public SimplePointColorMap(int initialCapacity) {
        this.keys = new Point[initialCapacity];
        this.values = new Color[initialCapacity];
        this.top = 0;
    }

    public int find(Point key) {
        for (int i = 0; i < top; i++) {
            if(keys[i] != null && key.compareTo(keys[i]) == 0){
                return i;
            }
        }
        return top;
    }

    // Adds a new key-value association to this map. If the key already exists in this map,
    // the value is replaced and the old value is returned. Otherwise, 'null' is returned.
    // Precondition: key != null && value != null.
    public Color put(Point key, Color value) {
        int index = find(key);

        // not found
        if(index == top) {
            if(++top == values.length) {
                doubleCapacity();
            }
        }

        Color oldValue = values[index];
        keys[index] = key;
        values[index] = value;
        return oldValue;
    }

    private void doubleCapacity() {
        Point[] newKeys = new Point[this.keys.length * 2];
        Color[] newValues = new Color[this.values.length * 2];

        for (int i = 0; i < this.values.length; i++) {
            newKeys[i] = this.keys[i];
            newValues[i] = this.values[i];
        }

        this.values = newValues;
        this.keys = newKeys;
    }

    // Returns the value associated with the specified key, i.e. the method returns the color
    // associated with the specified point.
    // More formally, if this map contains a mapping from a key k to a value v such that
    // key.compareTo(k) == 0, then this method returns v.
    // (There can be at most one such mapping.)
    // Returns 'null' if the key is not contained in this map.
    // Precondition: key != null.
    public Color get(Point key) {
        return this.values[find(key)];
    }

    // Removes the mapping for a key from this map if it is present. More formally, if this map
    // contains a mapping from key k to value v such that key.compareTo(k) == 0,
    // that mapping is removed. (The map can contain at most one such mapping.)
    // Returns the value to which this map previously associated the key, or 'null' if the map
    // contained no mapping for the key.
    // Precondition: key != null.
    public Color remove(Point key) {
        int index = find(key);

        // not found
        if(index == top) {
            return null;
        }

        Color oldValue = this.values[index];
        this.values[index] = null;
        this.keys[index] = null;
        return oldValue;
    }

    // Returns a queue with all keys of this map (ordering is not specified).
    public SimplePointQueue keys() {
        SimplePointQueue simplePointQueue = new SimplePointQueue(8);
        for (int i = 0; i < this.values.length; i++) {
            if(this.values[i] != null) {
                simplePointQueue.add(this.keys[i]);
            }
        }
        return simplePointQueue;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof SimplePointColorMap that)) return false;
        return top == that.top && Arrays.equals(values, that.values) && Arrays.equals(keys, that.keys);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(top);
        result = 31 * result + Arrays.hashCode(values);
        result = 31 * result + Arrays.hashCode(keys);
        return result;
    }
}