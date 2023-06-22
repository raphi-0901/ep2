import java.util.Scanner;

// A factory that creates a 'UnsafeNewLayerOperation' object.
//
public class UnsafeNewLayerFactory implements UnsafeFactory {

    @Override
    // Returns a new 'UnsafeNewLayerOperation' object.
    // sc has no effetcs
    public UnsafeOperation create(Scanner sc) {
        return new UnsafeNewLayerOperation();
    }
}
