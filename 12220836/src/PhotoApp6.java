import codedraw.CodeDraw;

import java.awt.*;
import java.util.HashMap;
import java.util.Scanner;

public class PhotoApp6 {

    public static void main(String[] args) {

        // TODO: modify according to 'Aufgabenblatt6.md'.

        // TODO: declare and initialize 'Layered' variable.
        Layered raster = new MultiLayerRasterRGBA(new TreeSparseRasterRGBA(40, 60), null);

        int cellSize = 10;
        CodeDraw cd = new CodeDraw(raster.getWidth() * cellSize, raster.getHeight() * cellSize);
        cd.clear(Color.BLACK);

        Scanner sc = new Scanner(System.in);

        // set default color.
        Color[] c = {Color.GREEN};
        // The variable c contains the default color as an array entry. An array is
        // used because it enables the default color to be changed by other classes after
        // it has been passed to a factory object (multiple objects use identical array).


        String input =
            "line 2 3 10 20\n" +
            "newlayer\n" +
            "setcolor 255 0 255 255\n" +
            "line 0 10 17 7\n" +
            "filter\n" +
            "newlayer\n" +
            "setcolor 255 0 0 255 \n" +
            "line 0 30 19 0\n" +
            "newlayer\n" +
            "setcolor 255 255 255 255\n" +
            "fill 10 11\n" +
            "crop 15 20\n";

        sc = new Scanner(input);

        // set the filter kernel for all blurring operations in this app.
        double[][] filterKernel = new double[][]{
                {0.077847, 0.123317, 0.077847},
                {0.123317, 0.195346, 0.123317},
                {0.077847, 0.123317, 0.077847}
        };

        // HashMap<String, UnsafeFactory> is a predefined associative data structure in the
        // Java libraries implemented using a hash table, where keys are of type 'String' and
        // associated values of type 'UnsafeFactory'.
        HashMap<String, UnsafeFactory> commandMap = new HashMap<String, UnsafeFactory>();
        // TODO: put key-value associations to 'commandMap': keys are command strings (like "line"
        //  or "fill"), values are corresponding factories.
        commandMap.put("line", new UnsafeLineFactory(c));
        commandMap.put("fill", new UnsafeFillFactory(c));
        commandMap.put("crop", new UnsafeCropFactory());
        commandMap.put("filter", new UnsafeConvolveFactory(filterKernel));
        commandMap.put("newlayer", new UnsafeNewLayerFactory());

        while (sc.hasNext()) {
            String command = sc.next();

            if (command.equals("setcolor")) {
                // command that does not involve a raster
                c[0] = new Color(sc.nextInt(), sc.nextInt(), sc.nextInt(), sc.nextInt());
            } else {
                // commands with a corresponding factory object creating an operation on a raster.
                commandMap.get(command).create(sc).execute(raster);
            }
        }

        cd.close();
        cd = new CodeDraw(raster.getWidth() * cellSize, raster.getHeight() * cellSize);
        cd.clear(Color.BLACK);

        // finally visualize all layers separately.
        for (RasterizedRGB layer : raster) {
            // draw a square of size 'cellSize' for each pixel
            for (int j = 0; j < layer.getHeight(); j++) {
                for (int i = 0; i < layer.getWidth(); i++) {
                    int x = i * cellSize;
                    int y = j * cellSize;
                    cd.setColor(layer.getPixelColor(i, j));
                    cd.fillSquare(x, y, cellSize);
                }
            }
        }

        cd.show();
    }
}
