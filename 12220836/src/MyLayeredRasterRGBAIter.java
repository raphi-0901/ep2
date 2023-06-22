// Represents a raster consisting of at least two layers.
//
public class MyLayeredRasterRGBAIter implements RasterizedRGBIterator
{
    private LinkedListRasterRGBA layers;
    private int index = 0;

    public MyLayeredRasterRGBAIter(LinkedListRasterRGBA layers) {
        this.layers = layers;
    }

    @Override
    public RasterizedRGB next() {
        if(!hasNext()) {
            return null;
        }

        RasterizedRGB result = layers.get(index);
        index++;
        return result;
    }

    @Override
    public boolean hasNext() {
        return index < layers.size();
    }
}
