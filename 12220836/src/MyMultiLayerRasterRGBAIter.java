// Represents a raster consisting of at least two layers.
//
public class MyMultiLayerRasterRGBAIter implements RasterizedRGBIterator
{
    private Layered raster;

    public MyMultiLayerRasterRGBAIter(Layered raster) {
        this.raster = raster;
    }

    @Override
    public RasterizedRGB next() {
        Layered result = raster.getForeground();
        if(result == null) {
            result = raster;
        }

        raster = raster.getBackground();
        return result;
    }

    @Override
    public boolean hasNext() {
        if(raster == null) {
            return false;
        }

        if(raster instanceof SingleLayer) {
            return true;
        }

        return raster.getForeground() != null;
    }
}
