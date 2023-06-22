// A list of objects of 'RasterRGBA' implemented as a doubly linked list.
// The number of elements of the list is not limited. Entries of 'null' are allowed.
//
// TODO: define further classes and methods for the implementation of the doubly linked list, if
//  needed.
//
public class LinkedListRasterRGBA {

    //TODO: declare variables.
    MyLinkedListRasterNode head, tail;


    // Initializes 'this' as an empty list.
    public LinkedListRasterRGBA() {
    }

    // Inserts the specified element 'raster' at the beginning of this list.
    public void addFirst(RasterRGBA raster) {
        if (head == null && tail == null) {
            head = tail = new MyLinkedListRasterNode(raster, null, null);
            return;
        }
        MyLinkedListRasterNode node = new MyLinkedListRasterNode(raster, head, null);

        if(head != null) {
            head.setPrevious(node);
        }
        head = node;
    }

    // Appends the specified element 'raster' to the end of this list.
    public void addLast(RasterRGBA raster) {
        if (head == null && tail == null) {
            head = tail = new MyLinkedListRasterNode(raster, null, null);
            return;
        }

        MyLinkedListRasterNode node = new MyLinkedListRasterNode(raster, null, tail);
        tail.setNext(node);
        tail = node;
    }

    // Returns the last element in this list.
    // Returns 'null' if the list is empty.
    public RasterRGBA getLast() {
        if(tail == null) {
            return null;
        }
        return tail.getValue();
    }

    // Returns the first element in this list.
    // Returns 'null' if the list is empty.
    public RasterRGBA getFirst() {
        if(head == null) {
            return null;
        }
        return head.getValue();
    }

    // Retrieves and removes the first element in this list.
    // Returns 'null' if the list is empty.
    public RasterRGBA pollFirst() {
        if (head == null) {
            return null;
        }

        RasterRGBA value = head.getValue();
        head = head.getNext();

        if (head == null) {
            tail = null;
        } else {
            head.setPrevious(null);
        }

        return value;
    }

    // Retrieves and removes the last element in this list.
    // Returns 'null' if the list is empty.
    public RasterRGBA pollLast() {
        if(tail == null) {
            return null;
        }

        RasterRGBA value = tail.getValue();
        tail = tail.getPrevious();

        if (tail == null) {
            head = null;
        } else {
            tail.setNext(null);
        }

        return value;
    }

    // Inserts the specified element 'raster' at the specified position in this list.
    // More specifically, 'raster' is inserted as follows:
    // before insertion elements have indices from 0 to size()-1;
    // 'raster' is inserted immediately before the element with the given index 'i' (or as last
    // element if 'i == size()') such that 'raster' can be found at index 'i' after insertion.
    // Precondition: i >= 0 && i <= size().
    public void add(int i, RasterRGBA raster) {
        MyLinkedListRasterNode current = head;
        for (int j = 0; j < i; j++) {
            current = current.getNext();
        }

        // last element
        if(current == null) {
            addLast(raster);
            return;
        }

        // first element
        if(current == head) {
            addFirst(raster);
            return;
        }

        MyLinkedListRasterNode node = new MyLinkedListRasterNode(raster, current, current.getPrevious());
        current.getPrevious().setNext(node);
        current.setPrevious(node);
    }

    // Returns the element at the specified position in this list.
    // Precondition: i >= 0 && i < size().
    public RasterRGBA get(int i) {
        MyLinkedListRasterNode current = head;
        for (int j = 0; j < i; j++) {
            current = current.getNext();
        }
        return current.getValue();
    }

    // Replaces the element at the specified position in this list with the specified element.
    // Returns the element that was replaced.
    // Precondition: i >= 0 && i < size().
    public RasterRGBA set(int i, RasterRGBA raster) {
        MyLinkedListRasterNode current = head;
        for (int j = 0; j < i; j++) {
            current = current.getNext();
        }
        RasterRGBA oldValue = current.getValue();
        current.setValue(raster);
        return oldValue;
    }

    // Removes the element at the specified position in this list. Shifts any subsequent
    // elements to the left (subtracts one from their indices). Returns the element that was
    // removed from the list.
    // Precondition: i >= 0 && i < size().
    public RasterRGBA remove(int i) {
        MyLinkedListRasterNode current = head;
        for (int j = 0; j < i; j++) {
            current = current.getNext();
        }

        if(current == head) {
            head = current.getNext();
        }

        if(current == tail) {
            tail = current.getPrevious();
        }

        if (current.getPrevious() != null) {
            current.getPrevious().setNext(current.getNext());
        }

        if (current.getNext() != null) {
            current.getNext().setPrevious(current.getPrevious());
        }

        return current.getValue();
    }

    // Returns the index of the last occurrence of 'raster' in this list (the highest index with an
    // element equal to 'raster'), or -1 if this list does not contain the element.
    // Equality of elements is determined by object identity (== operator).
    public int lastIndexOf(RasterRGBA raster) {
        MyLinkedListRasterNode current = tail;
        int index = size() - 1;

        while(current != null) {
            if (raster == current.getValue()) {
                return index;
            }
            current = current.getPrevious();
            index--;
        }

        return -1;
    }

    // Returns the number of elements in this list.
    public int size() {
        int counter = 0;
        MyLinkedListRasterNode current = head;
        while (current != null) {
            counter++;
            current = current.getNext();
        }
        return counter;
    }

    //TODO (optional): add more operations (e.g., floodfill).
}

// TODO: define further classes, if needed (either here or in a separate file).
