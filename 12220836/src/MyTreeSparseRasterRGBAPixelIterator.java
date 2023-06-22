import java.awt.*;

public class MyTreeSparseRasterRGBAPixelIterator implements MyPixelIterator {
    private TreeSparseRasterRGBA raster;

    private int x;
    private int y;

    public MyTreeSparseRasterRGBAPixelIterator(TreeSparseRasterRGBA raster) {
        this.raster = raster;
    }

    @Override
    public Color next() {
        Color color = raster.getPixelColor(x,y);
        skipToNextColor();
        return color;
    }

    @Override
    public boolean hasNext() {
        if(x > raster.getWidth()) {
            return false;
        }

        if(y > raster.getHeight()) {
            return false;
        }

        if(raster.getPixelColor(x,y).equals(Color.BLACK)) {
            skipToNextColor();
            return hasNext();
        }

        return true;
    }

    private void skipToNextColor() {
        if(x >= raster.getWidth()) {
            x = 0;
            y++;
        }
        else {
            x++;
        }
    }
}
