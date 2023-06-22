import java.awt.*;

// This class represents a flood fill operation. More specifically, it allows to set the area of
// contiguous pixels of the same color to a specified color, starting from an initial position and
// using 4-neighborhood.
//
public class UnsafeFillOperation implements UnsafeOperation {

    private int x;
    private int y;
    private Color color;

    // Initialises this fill operation with starting point (x, y)
    // and the color of the area.
    public UnsafeFillOperation(int x, int y, Color color) {
        this.x = x;
        this.y = y;
        this.color = color;
    }

    // Executes the operation. More specifically, this method floodfills the area in a 4-neighboorhod
    // The specified object is directly modified by this method call.
    // The returned raster is identical to the specified 'raster'.
    // Precondition:
    // x and y are valid positions of 'raster'
    // color != null
    public RasterizedRGB execute(RasterizedRGB raster) {
        int[][] directions = {{0, -1}, {-1, 0}, {0, 1}, {1, 0}};

        // Get the color of the initial pixel
        Color startColor = raster.getPixelColor(x, y);

        // If the initial pixel already has the target color, do nothing
        if (startColor == color) {
            return raster;
        }

        // Create a queue and add the initial pixel
        SimplePointQueue queue = new SimplePointQueue(4);
        queue.add(new Point(x, y));

        // Process the queue until it's empty
        while (queue.size() != 0) {
            // Get the next position to process
            Point p = queue.poll();

            // If the pixel has the start color, set it to the target color
            Color colorOfPoint = raster.getPixelColor(p.getX(), p.getY());
            if (colorOfPoint.equals(startColor)) {
                raster.setPixelColor(p.getX(), p.getY(), color);

                for (int[] direction : directions) {
                    int newX = p.getX() + direction[0];
                    int newY = p.getY() + direction[1];
                    queue.add(new Point(newX, newY));
                }
            }
        }

        return raster;
    }
}
