import java.awt.*;

// A list of 2D-points (objects of type 'Point') implemented as a linked list.
// The number of elements of the list is not limited.
//
// TODO: define further classes and methods for the implementation of the linked list,
//  if needed.
//
public class MyTreeNode {

    //TODO: declare variables.
    private Color value;
    private Point key;
    private MyTreeNode left;
    private MyTreeNode right;


    public MyTreeNode(Point key, Color value) {
        this.key = key;
        this.value = value;
    }

    public void add(Point key, Color value) {
        if (key.compareTo(this.key) == -1) {
            if (left != null) {
                left.add(key, value);
            } else {
                left = new MyTreeNode(key, value);
            }
            return;
        }

        if (key.compareTo(this.key) == 1) {
            if (right != null) {
                right.add(key, value);
            } else {
                right = new MyTreeNode(key, value);
            }
        }
    }

    public void addToList(PointLinkedList pointLinkedList) {
        if (left != null) {
            left.addToList(pointLinkedList);
        }
        pointLinkedList.addLast(key);
        if (right != null) {
            right.addToList(pointLinkedList);
        }
    }

    public Color put(Point key, Color value) {
        MyTreeNode node = findNode(key);

        if (node == null) {
            add(key, value);
            return null;
        }
        Color oldValue = node.getValue();
        node.setValue(value);
        return oldValue;
    }

    public MyTreeNode findNode(Point key) {
        if (key.compareTo(this.key) == 0) {
            return this;
        }

        if (key.compareTo(this.key) == -1 && left != null) {
            return left.findNode(key);
        }

        return right != null ? right.findNode(key) : null;
    }

    public boolean contains(Point key) {
        if (key.compareTo(this.key) == 0) {
            return true;
        }

        if (key.compareTo(this.key) == -1) {
            return left != null && left.contains(key);
        }

        return right != null && right.contains(key);
    }

    public Color getValue() {
        return value;
    }

    public void setValue(Color value) {
        this.value = value;
    }

    public Point getKey() {
        return key;
    }

    public void setKey(Point key) {
        this.key = key;
    }

    public MyTreeNode getLeft() {
        return left;
    }

    public MyTreeNode getRight() {
        return right;
    }
}