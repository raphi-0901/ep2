import java.util.Scanner;

// A factory that creates a 'SafeNewLayerOperation' object.
//
public class SafeNewLayerFactory implements SafeFactory
{
    public SafeOperation create(Scanner sc) { // does not throw FactoryException
        return new SafeNewLayerOperation();
    }
}
