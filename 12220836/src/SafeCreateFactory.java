import java.util.NoSuchElementException;
import java.util.Scanner;

// A factory that creates an 'SafeCreateOperation' object.
//
public class SafeCreateFactory implements SafeFactory {
    @Override
    public SafeOperation create(Scanner sc) throws FactoryException {
        try {
            return new SafeCreateOperation(sc.nextInt(), sc.nextInt());
        } catch (NoSuchElementException exception) {
            throw new FactoryException();
        }
    }
}
