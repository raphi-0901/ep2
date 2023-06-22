import java.util.ArrayList;

// Leaf node of a mobile. The actual decoration of a mobile.
// A 'Star' has a specified weight of type 'int', that can not be changed after
// initialisation. 'Star' implements 'Decoration'.
//
public class Star implements Decoration // TODO: uncomment clause.
{

    //TODO: define missing parts of the class.
    private int weight;

    public Star(int weight) {
        this.weight = weight;
    }

    @Override
    public int getWeight() {
        return weight;
    }

    // Returns a readable representation of 'this' with the
    // symbol '*' followed by the weight of this star.
    public String toString() {
        return "*"+getWeight();
    }

    @Override
    public Countable getStickCountable() {
        return new StarCountable();
    }

    @Override
    public StarIterator iterator() {
        ArrayList<Star> starAsList = new ArrayList<>();
        starAsList.add(this);
        return new StarIter(starAsList);
    }
}

class StarCountable implements Countable {
    @Override
    public int count() {
        return 1;
    }
}
// TODO: define additional classes if needed (either here or in a separate file).
