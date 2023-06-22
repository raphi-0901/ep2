public class SafeNewLayerOperation implements SafeOperationSingle
{
    @Override
    public RasterizedRGB execute(RasterizedRGB raster) throws OperationException {
        if(raster == null) {
            throw new OperationException();
        }

        return new MultiLayerRasterRGBA(new TreeSparseRasterRGBA(raster.getWidth(), raster.getHeight()), raster);
    }

    @Override
    public String toString() {
        return "newlayer";
    }}
