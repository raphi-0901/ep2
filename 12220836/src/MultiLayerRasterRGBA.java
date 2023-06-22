import java.awt.*;
import java.util.ArrayList;

// Represents a raster consisting of at least two layers.
//
public class MultiLayerRasterRGBA implements Layered, RasterizedRGBIterable {
    SingleLayer foreground;
    Layered background;

    // Initializes 'this' with top-layer 'foreground' and 'background'.
    // Performs dynamic type checking of 'background'. If 'background' is an instance of 'Layered'
    // this constructor initializes 'this' with top-layer 'foreground' and layers of the
    // 'background'.
    // If 'background' is not an instance of 'Layered', 'background' is copied to a new object of
    // 'SingleLayer' which is then used to initialize the background.
    // Width and height of this raster is determined by width and height of the 'foreground'
    // raster.
    // Pixels that are not defined in the 'background' are assumed to have color (0,0,0,0).
    public MultiLayerRasterRGBA(SingleLayer foreground, RasterizedRGB background) {
        this.foreground = foreground;

        if (background instanceof Layered) {
            this.background = (Layered) background;
        } else if (background != null) {
            SingleLayer copiedBackground = new TreeSparseRasterRGBA(getWidth(), getHeight());
            background.copyTo(copiedBackground);
            this.background = copiedBackground;
        }
    }

    @Override
    public Layered newLayer() {
        MultiLayerRasterRGBA newRaster = new MultiLayerRasterRGBA(new TreeSparseRasterRGBA(getWidth(), getHeight()), this);
        return newRaster;
    }

    @Override
    public int numberOfLayers() {
        if(background ==  null) {
            return 1;
        }
        return background.numberOfLayers() + 1;
    }

    @Override
    public SingleLayer getForeground() {
        return foreground;
    }

    @Override
    public Layered getBackground() {
        return background;
    }

    @Override
    public int getWidth() {
        return foreground.getWidth();
    }

    @Override
    public int getHeight() {
        return foreground.getHeight();
    }

    @Override
    public Color getPixelColor(int x, int y) {
        Color color = foreground.getPixelColor(x, y);
        for (RasterizedRGB rasterizedRGB : this) {
            color = RasterRGBA.over(color, rasterizedRGB.getPixelColor(x, y));
        }

        return color;
    }

    @Override
    public void setPixelColor(int x, int y, Color color) {
        foreground.setPixelColor(x, y, color);
    }

    @Override
    public void convolve(double[][] filterKernel) {
        foreground.convolve(filterKernel);
    }

    @Override
    public void crop(int width, int height) {
        foreground.crop(width, height);

        for (RasterizedRGB rasterizedRGB : this) {
            rasterizedRGB.crop(width, height);
        }
    }

    @Override
    public RasterizedRGBIterator iterator() {
        return new MyMultiLayerRasterRGBAIter(this);
    }

    // Returns an iterator that iterates over the layers of this raster in a bottom-to-top order.
// (The first iteration returns the bottom-most layer.)
    public RasterizedRGBIterator reversedIterator() {
        RasterizedRGBIterator iterator = this.iterator();
        MultiLayerRasterRGBA invertedRaster = new MultiLayerRasterRGBA(null, null);
        while (iterator.hasNext()) {
            invertedRaster = new MultiLayerRasterRGBA((SingleLayer) iterator.next(), invertedRaster);
        }
        return new MyMultiLayerRasterRGBAIter(invertedRaster);
    }

    // Returns a new list with all the layers of this raster in top-to-bottom order. The size of the
// list equals the value returned by 'numberOfLayers()'.
    public java.util.ArrayList<RasterizedRGB> asList() {
        RasterizedRGBIterator iterator = this.iterator();
        ArrayList<RasterizedRGB> list = new ArrayList<>();
        while (iterator.hasNext()) {
            list.add(iterator.next());
        }
        return list;
    }
}
