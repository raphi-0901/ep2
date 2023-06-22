import java.awt.*;
import java.util.Scanner;

// A factory that creates a 'UnsafeFillOperation' object.
//
public class UnsafeFillFactory implements UnsafeFactory {

    Color[] color;

    // Initialises 'this' with the specified 'color' array.
    // Only the first value in this color array is defining the color
    // Precondition:
    // color != null
    // color[0] != null.
    public UnsafeFillFactory(Color[] color) {
        this.color = color;
    }

    // Returns a new 'UnsafeFillOperation' object. The 'x' and 'y' points of the starting point
    // are provided by the scanner object 'sc'.
    public UnsafeFillOperation create(Scanner sc) {
        int x = sc.nextInt();
        int y = sc.nextInt();
        return new UnsafeFillOperation(x, y, color[0]);
    }
}
