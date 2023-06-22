import java.awt.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

// This class represents a sparse 2D raster of RGB color entries.
//
// This class is efficient for representing images where only a small fraction of pixels is
// non-empty, meaning they have a color different from (0,0,0) corresponding to Color.BLACK.
// The class uses internally an object of 'HashPointColorMap' to associate each non-empty
// pixel position (x,y) to the corresponding color. Only pixels with non-zero values are stored.
// Positions that are not in the set of keys of the map are considered to have value (0,0,0).
//
public class HashSparseRasterRGB implements RasterizedRGB {

    private HashPointColorMap map;
    private int width;
    private int height;

    // Initialises this raster of the specified size as an empty
    // raster (all pixels being black, i.e. (R,G,B) = (0,0,0)).
    // Preconditions: height > 0, width > 0
    public HashSparseRasterRGB(int width, int height) {
        map = new HashPointColorMap();
        this.width = width;
        this.height = height;
    }

    // Returns the color of the specified pixel.
    // Preconditions: (x,y) is a valid coordinate of the raster
    public Color getPixelColor(int x, int y) {
        Color color = map.get(new Point(x, y));
        if (color == null) {
            return Color.BLACK;
        }

        return color;
    }

    // Sets the color of the specified pixel. (If 'color' is 'Color.BLACK', the method
    // ensures that the pixel is not contained in the internal map.)
    // Preconditions: (x,y) is a valid coordinate of the raster, color != null
    public void setPixelColor(int x, int y, Color color) {
        if (color.equals(Color.BLACK)) {
            return;
        }

        map.put(new Point(x, y), color);
    }

    // Returns the width of this raster.
    public int getWidth() {
        return this.width;
    }

    // Returns the height of this raster.
    public int getHeight() {
        return this.height;
    }

    // Performs the convolution of 'this' with the specified filter kernel. 'this' is the result of
    // the operation.
    // The implementation of this method exploits the sparse structure of the raster by
    // calculating the convolution only at non-empty pixel positions.
    // Preconditions:
    // filterKernel != null && filterKernel.length > 0 &&
    // filterKernel.length % 2 == 1 &&
    // filterKernel.length == filterKernel[i].length (for valid i) &&
    // filterKernel.length < this.getWidth() &&
    // filterKernel.length < this.getHeight().
    public void convolve(double[][] filterKernel) {
        double[] temp_result;
        HashPointColorMap newMap = new HashPointColorMap();
        int filterSideLength = filterKernel.length / 2;
        for (int x = filterSideLength; x < this.width - filterSideLength; x++) {
            for (int y = filterSideLength; y < this.height - filterSideLength; y++) {
                //at every array position, compute filter result
                temp_result = new double[3];
                for (int xx = -filterSideLength; xx <= filterSideLength; xx++) {
                    for (int yy = -filterSideLength; yy <= filterSideLength; yy++) {
                        Color color = this.getPixelColor(x - xx, y - yy);
                        temp_result[0] += color.getRed() * filterKernel[xx + filterSideLength][yy + filterSideLength];
                        temp_result[1] += color.getGreen() * filterKernel[xx + filterSideLength][yy + filterSideLength];
                        temp_result[2] += color.getBlue() * filterKernel[xx + filterSideLength][yy + filterSideLength];
                    }
                }
                Color newColor = new Color((int) temp_result[0], (int) temp_result[1], (int) temp_result[2]);
                newMap.put(new Point(x, y), newColor);
            }
        }
        map = newMap;
    }

    // Crops 'this' to the rectangular region with upper left coordinates (0,0)
    // and lower right coordinates (width-1, height-1).
    // Precondition: width <= this.getWidth() && height <= this.getHeight().
    public void crop(int width, int height) {
        HashPointColorMap newMap = new HashPointColorMap();
        this.width = width;
        this.height = height;

        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                Point point = new Point(x, y);
                newMap.put(point, map.get(point));
            }
        }

        map = newMap;
    }

    @Override
    public String toString() {
        HashMap<Color, Integer> colorMap = new HashMap<>();
        SimplePointQueue points = map.keys();

        colorMap.put(Color.BLACK, height*width - points.size());
        for (int i = 0; i < points.size(); i++) {
            Point point = points.poll();
            Color color = map.get(point);

            Integer occurrences = colorMap.get(color);
            if (occurrences == null) {
                occurrences = 0;
            }
            colorMap.put(color, occurrences + 1);
        }

        int pixels = height * width;
        String output = pixels + " pixels, {";
        for (Map.Entry<Color, Integer> entry : colorMap.entrySet()) {
            output += entry.getKey() + "=" + entry.getValue() + ",\n";
        }

        output = output.substring(0, output.length() - 2) + "}";
        return output;
    }
}
