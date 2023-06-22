import java.awt.*;


// This class represents a 2D raster of RGB color entries. The class uses
// the class 'SimpleDataBufferInt' to store the entries.
public class SimpleRasterRGB {

    // TODO: make these two variables 'private' and remove 'static'.
    private int width = 40;
    private int height = 60;

    // TODO: add more object variables, if needed.
    private SimpleDataBufferInt buffer;

    // Initialises this raster of the specified size with
    // all pixels being black, i.e. (R,G,B) = (0,0,0).
    // Preconditions: height > 0, width > 0
    public SimpleRasterRGB(int width, int height) {
        this.width = width;
        this.height = height;
        this.buffer = new SimpleDataBufferInt(6, height * width);
    }

    public SimpleRasterRGB(SimpleRasterRGB simpleRasterRGB) {
        this.width = simpleRasterRGB.getWidth();
        this.height = simpleRasterRGB.getHeight();
        this.buffer = new SimpleDataBufferInt(simpleRasterRGB.buffer);
    }

    // Returns the color of the specified pixel.
    // Precondition: (x,y) is a valid coordinate of the raster
    public Color getPixelColor(int x, int y) {
        Color color = new Color(this.buffer.getElem(0, y * this.width + x),
                this.buffer.getElem(1, y * this.width + x), this.buffer.getElem(2,
                y * this.width + x));
        return color;
    }

    public SimpleRasterRGB addBottom(int n, Color color) {
        SimpleRasterRGB newSimpleRaster = new SimpleRasterRGB(width, height + n);
        newSimpleRaster.buffer = new SimpleDataBufferInt(6, (height + n) * (width));

        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                Color oldColor = this.getPixelColor(i, j);
                if (color == null) {
                    newSimpleRaster.setPixelColor(i, j, Color.black);
                } else {
                    newSimpleRaster.setPixelColor(i, j, oldColor);
                }
            }
        }

        if (color != null) {
            for (int i = 0; i < width; i++) {
                for (int j = height; j < height + n; j++) {
                    newSimpleRaster.setPixelColor(i, j, color);
                }
            }
        }

        return newSimpleRaster;
    }

    // Sets the color of the specified pixel.
    // Precondition: (x,y) is a valid coordinate of the raster, color != null
    public void setPixelColor(int x, int y, Color color) {
        this.buffer.setElem(0, y * this.width + x, color.getRed());
        this.buffer.setElem(1, y * this.width + x, color.getGreen());
        this.buffer.setElem(2, y * this.width + x, color.getBlue());
    }

    // Returns the result of convolution of 'this' with the specified filter kernel. 'this' is not
    // changed by the method call.
    // Precondition (needs to be checked):
    // filterKernel != 0 && filterKernel.length > 0 &&
    // filterKernel.length % 2 == 1 &&
    // filterKernel.length == filterKernel[0].length &&
    // filterKernel.length < this.getWidth() &&
    // filterKernel.length < this.getHeight().
    public SimpleRasterRGB convolve(double[][] filterKernel) {
        SimpleRasterRGB toBeFiltered = new SimpleRasterRGB(width, height);
        double[] temp_result;

        int filterSideLength = filterKernel.length / 2;
        for (int x = filterSideLength; x < toBeFiltered.width - filterSideLength; x++) {
            for (int y = filterSideLength; y < toBeFiltered.height - filterSideLength; y++) {
                //at every array position, compute filter result
                temp_result = new double[3];
                for (int xx = -filterSideLength; xx <= filterSideLength; xx++) {
                    for (int yy = -filterSideLength; yy <= filterSideLength; yy++) {
                        temp_result[0] += this.getPixelColor(x - xx, y - yy).getRed() * filterKernel[xx + filterSideLength][yy + filterSideLength];
                        temp_result[1] += this.getPixelColor(x - xx, y - yy).getGreen() * filterKernel[xx + filterSideLength][yy + filterSideLength];
                        temp_result[2] += this.getPixelColor(x - xx, y - yy).getBlue() * filterKernel[xx + filterSideLength][yy + filterSideLength];
                    }
                }

                toBeFiltered.buffer.setElem(0, y * this.width + x, (int) temp_result[0]);
                toBeFiltered.buffer.setElem(1, y * this.width + x, (int) temp_result[1]);
                toBeFiltered.buffer.setElem(2, y * this.width + x, (int) temp_result[2]);
            }
        }
        return toBeFiltered;
    }


    // Returns the result of convolution of the specified raster 'toBeFiltered' with the specified
    // filter kernel 'filterKernel'.
    // Precondition (needs not be checked):
    // filterKernel != null && filterKernel.length > 0 &&
    // filterKernel.length % 2 == 1 &&
    // filterKernel.length == filterKernel[0].length &&
    // filterKernel.length < this.getWidth() &&
    // filterKernel.length < this.getHeight().
    public static SimpleRasterRGB convolve(SimpleRasterRGB toBeFiltered, double[][] filterKernel) {
        return toBeFiltered.convolve(filterKernel);
    }

    // Returns the width of this raster.
    public int getWidth() {
        return width;
    }

    // Returns the height of this raster.
    public int getHeight() {
        return height;
    }

    // Draws a line from (x1,y1) to (x2,y2) in the raster using the Bresenham algorithm.
    //Preconditions: (x1,y1) and (x2,y2) are valid coordinates of the raster, color != null
    public void drawLine(int x1, int y1, int x2, int y2, Color color) {
        int dx = Math.abs(x2 - x1);
        int dy = Math.abs(y2 - y1);
        int sx = x1 < x2 ? 1 : -1;
        int sy = y1 < y2 ? 1 : -1;
        int err = dx - dy;

        while (x1 != x2 || y1 != y2) {
            this.setPixelColor(x1, y1, color);

            int err2 = 2 * err;
            if (err2 > -dy) {
                err -= dy;
                x1 += sx;
            }
            if (err2 < dx) {
                err += dx;
                y1 += sy;
            }
        }
    }

    // Returns a mapping from all width*height pixel positions (Point) to corresponding colors
    // (Color) of the pixels. Values of color (0,0,0) are also included in the mapping.
    public TreePointColorMap asMap() {
        TreePointColorMap map = new TreePointColorMap();

        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                map.put(new Point(i, j), getPixelColor(i, j));
            }
        }
        // TODO: implement method.
        return map;
    }
}
