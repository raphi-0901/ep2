public class SimplePointQueue {

    private Point[] queue;
    private int tail, head;

    // Initializes this queue with an initial capacity (length of internal array).
    // Precondition: initialCapacity > 0.
    public SimplePointQueue(int initialCapacity) {
        this.queue = new Point[initialCapacity];
        this.head = 0;
        this.tail = 0;
    }

    public int capacity() {
        int count = 0;
        for (Point point : this.queue) {
            if (point != null) {
                count++;
            }
        }
        return count;
    }

    // Adds the specified point 'p' to this queue.
    // Precondition: p != null.
    public void add(Point p) {
        if ((tail+1) % this.queue.length == head) {
            doubleCapacity();
        }

        this.queue[tail] = p;
        tail = (tail + 1) % this.queue.length;
    }

    private void doubleCapacity() {
        Point[] newQueue = new Point[this.queue.length * 2];
        int j = 0;
        for (int i = 0; i < this.queue.length; i++) {
            if(this.queue[i] != null) {
                newQueue[j] = this.queue[i];
                j++;
            }
        }
        head = 0;
        tail = j;
        this.queue = newQueue;
    }

    // Retrieves and removes the head of this queue, or returns 'null'
    // if this queue is empty.
    public Point poll() {
        if (size() == 0) {
            return null;
        }
        Point value = this.queue[head];
        this.queue[head] = null;
        head = (head + 1) % queue.length;

        if((double)capacity() / (double) queue.length< 0.3) {
            reduceCapacity();
        }

        return value;
    }

    public void reduceCapacity() {
        Point[] newQueue = new Point[(int)(this.queue.length * 0.6)];
        int j = 0;
        for (int i = 0; i < this.queue.length; i++) {
            if(this.queue[i] != null) {
                newQueue[j] = this.queue[i];
                j++;
            }
        }
        head = 0;
        tail = j;
        queue = newQueue;
    }

    // Retrieves, but does not remove the head of this queue, or returns 'null'
    // if this queue is empty.
    public Point peek() {
        if (size() == 0) {
            return null;
        }
        return this.queue[head];
    }

    // Returns the number of entries in this queue.
    public int size() {
//        return (tail + this.queue.length - head) % this.queue.length;
        int counter = 0;
        for (int i = 0; i < this.queue.length; i++) {
            if (this.queue[i] != null) {
                counter++;
            }
        }
        return counter;
    }
}
