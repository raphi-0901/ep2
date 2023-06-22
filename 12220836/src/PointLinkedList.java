// A list of 2D-points (objects of type 'Point') implemented as a linked list.
// The number of elements of the list is not limited.
//
// TODO: define further classes and methods for the implementation of the linked list,
//  if needed.
//
public class PointLinkedList {

    //TODO: declare variables.
    MyPointListNode head;


    // Inserts the specified element 'point' at the beginning of this list.
    // Precondition: point != null.
    public void addFirst(Point point) {
        this.head = new MyPointListNode(point, this.head);
    }

    // Appends the specified element 'point' to the end of this list.
    // Precondition: point != null.
    public void addLast(Point point) {
        if (this.head == null) {
            addFirst(point);
            return;
        }

        MyPointListNode next = this.head;
        while (next.getNext() != null) {
            next = next.getNext();
        }

        next.setNext(new MyPointListNode(point, null));
    }

    // Returns the last element in this list.
    // Returns 'null' if the list is empty.
    public Point getLast() {
        if (this.head == null) {
            return null;
        }

        MyPointListNode next = this.head;
        while (next.getNext() != null) {
            next = next.getNext();
        }

        return next.getValue();
    }

    // Returns the first element in this list.
    // Returns 'null' if the list is empty.
    public Point getFirst() {
        if (this.head == null) {
            return null;
        }
        return this.head.getValue();
    }

    // Retrieves and removes the first element in this list.
    // Returns 'null' if the list is empty.
    public Point pollFirst() {
        if (head == null) {
            return null;
        }

        Point value = head.getValue();
        head = head.getNext();
        return value;
    }

    // Retrieves and removes the last element in this list.
    // Returns 'null' if the list is empty.
    public Point pollLast() {
        if (head == null) {
            return null;
        }

        if (head.getNext() == null) {
            return null;
        }

        MyPointListNode secondLast = head;
        while (secondLast.getNext().getNext() != null) {
            secondLast = secondLast.getNext();
        }
        Point value = secondLast.getNext().getValue();
        secondLast.setNext(null);
        return value;
    }

    public void removeInnerSublist(int fromIndex, int toIndex) {
        MyPointListNode current = head;
        int index = 0;
        MyPointListNode from = null;
        MyPointListNode to = null;
        while (current != null) {
            if(fromIndex == index) {
                from = current;
            }

            if(toIndex -1  == index) {
                to = current.getNext();
                break;
            }
            current = current.getNext();
            index++;
        }

        if(from != null) {
            from.setNext(to);
        }
    }

    // Inserts the specified element 'point' at the specified position in this list.
    // Precondition: i >= 0 && i <= size() && point != null.
    public void add(int i, Point point) {
        if(head == null && i == 0) {
            addFirst(point);
            return;
        }

        MyPointListNode next = head;
        for (int j = 1; j < i; j++) {
            next = next.getNext();
        }

        MyPointListNode newPoint = new MyPointListNode(point, next.getNext() == null ? null : next.getNext());
        next.setNext(newPoint);
    }

    // Returns the element at the specified position in this list.
    // Precondition: i >= 0 && i < size().
    public Point get(int i) {
        if(head == null) {
            return null;
        }

        MyPointListNode next = head;
        for (int j = 0; j < i; j++) {
            next = next.getNext();
        }

        return next.getValue();
    }

    // Returns the index of the first occurrence (element with equal coordinates to 'point') of the
    // specified element in this list, or -1 if this list does not contain the element.
    // Precondition: point != null.
    public int indexOf(Point point) {
        MyPointListNode next = head;
        int i = 0;
        while (next != null) {
            if (next.getValue().compareTo(point) == 0) {
                return i;
            }
            next = next.getNext();
            i++;
        }
        return -1;
    }

    // Returns the number of elements in this list.
    public int size() {
        MyPointListNode next = head;
        int i = 0;
        while (next != null) {
            next = next.getNext();
            i++;
        }
        return i;
    }
}

// TODO: define further classes, if needed (either here or in a separate file).
