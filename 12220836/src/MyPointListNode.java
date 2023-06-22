// A list of 2D-points (objects of type 'Point') implemented as a linked list.
// The number of elements of the list is not limited.
//
// TODO: define further classes and methods for the implementation of the linked list,
//  if needed.
//
public class MyPointListNode {

    //TODO: declare variables.
    private Point value;
    private MyPointListNode next;


    public MyPointListNode(Point value, MyPointListNode next) {
        this.value = value;
        this.next = next;
    }

    public void setNext(MyPointListNode next) {
        this.next = next;
    }

    public MyPointListNode getNext() {
        return this.next;
    }

    public Point getValue() {
        return value;
    }

    public void setValue(Point value) {
        this.value = value;
    }
}