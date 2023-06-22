// This class represents an operation creating a new top-most layer.
//
public class UnsafeNewLayerOperation implements UnsafeOperation {

    // Executes the operation. It adds a new layer on top of the existing ones.
    // The specified object is directly modified by this method call.
    public RasterizedRGB execute(RasterizedRGB raster) {
        return new MultiLayerRasterRGBA(new TreeSparseRasterRGBA(raster.getWidth(), raster.getHeight()), raster);
    }
}
