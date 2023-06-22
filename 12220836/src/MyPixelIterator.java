import java.awt.*;
import java.util.Iterator;

// An iterator over elements of 'MyPixelIterator'.
//
public interface MyPixelIterator extends Iterator<Color> {

    @Override
        // Returns the next element in the iteration.
        // (Returns 'null' if the iteration has no more elements.)
    Color next();

    @Override
        // Returns 'true' if the iteration has more elements.
    boolean hasNext();
}
