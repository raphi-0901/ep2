public class MyLinkedListRasterNode {

    private MyLinkedListRasterNode previous;
    private MyLinkedListRasterNode next;
    private RasterRGBA value;


    public MyLinkedListRasterNode(RasterRGBA value, MyLinkedListRasterNode next, MyLinkedListRasterNode previous) {
        this.value = value;
        this.next = next;
        this.previous = previous;
    }

    public MyLinkedListRasterNode getPrevious() {
        return previous;
    }

    public void setPrevious(MyLinkedListRasterNode previous) {
        this.previous = previous;
    }

    public void setNext(MyLinkedListRasterNode next) {
        this.next = next;
    }

    public MyLinkedListRasterNode getNext() {
        return this.next;
    }

    public RasterRGBA getValue() {
        return value;
    }

    public void setValue(RasterRGBA value) {
        this.value = value;
    }
}