import java.awt.*;

public class MyTreeSetRasterNode {
    private MyTreeSetRasterNode left;
    private MyTreeSetRasterNode right;
    private RasterRGBA value;


    public MyTreeSetRasterNode(RasterRGBA value) {
        this.value = value;
    }

    public void add(RasterRGBA element) {
        int currentKey = getKey();
        int nodeKey = element.countPixels(new Color(0, 0, 0, 0));

        if (currentKey <= nodeKey) {
            if (left == null) {
                left = new MyTreeSetRasterNode(element);
            } else {
                left.add(element);
            }
            return;
        }

        if (right == null) {
            right = new MyTreeSetRasterNode(element);
        } else {
            right.add(element);
        }
    }

    public boolean contains(RasterRGBA element) {
        int currentKey = getKey();
        int nodeKey = element.countPixels(new Color(0, 0, 0, 0));

        if(value.equals(element)) {
            return true;
        }

        if (currentKey <= nodeKey) {
            if (left == null) {
                return false;
            }

            return left.contains(element);
        }

        if (right == null) {
            return false;
        }

        return right.contains(element);
    }

    public int getKey() {
        return value.countPixels(new Color(0, 0, 0, 0));
    }

    public MyTreeSetRasterNode getLeft() {
        return left;
    }

    public void setLeft(MyTreeSetRasterNode left) {
        this.left = left;
    }

    public MyTreeSetRasterNode getRight() {
        return right;
    }

    public void setRight(MyTreeSetRasterNode right) {
        this.right = right;
    }

    public RasterRGBA getValue() {
        return value;
    }

    public void setValue(RasterRGBA value) {
        this.value = value;
    }
}