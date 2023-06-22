public class SafeCreateOperation implements SafeOperationSingle {
    int width;
    int height;

    public SafeCreateOperation(int width, int height) {
        this.width = width;
        this.height = height;
    }

    @Override
    public RasterizedRGB execute(RasterizedRGB raster) throws OperationException {
        return new MultiLayerRasterRGBA(new TreeSparseRasterRGBA(this.width, this.height), null);
    }

    @Override
    public String toString() {
        return "create " + this.width + " " + this.height;
    }
}
