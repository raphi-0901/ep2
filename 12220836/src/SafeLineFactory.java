import java.awt.*;
import java.util.NoSuchElementException;
import java.util.Scanner;

// A factory that creates an 'SafeLineOperation' object.
//
public class SafeLineFactory implements SafeFactory {
    private Color color[];

    public SafeLineFactory(Color[] color) {
        this.color = color;
    }

    @Override
    public SafeOperation create(Scanner sc) throws FactoryException {
        try {
            int[] coordinates = new int[]{
                    sc.nextInt(), sc.nextInt(), sc.nextInt(), sc.nextInt()
            };

            for (int coordinate : coordinates) {
                if (coordinate < 0) {
                    throw new FactoryException();
                }
            }

            return new SafeLineOperation(coordinates[0], coordinates[1], coordinates[2], coordinates[3], color[0]);
        } catch (NoSuchElementException exception) {
            throw new FactoryException();
        }
    }
}
