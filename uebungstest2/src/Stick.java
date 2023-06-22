import java.util.ArrayList;
import java.util.Arrays;
import java.util.NoSuchElementException;
import java.util.Objects;

// A 'Stick' has a specified stick weight, that can not be changed after
// initialisation. Mobiles can be attached to the stick (recursive structure).
// 'Stick' implements 'Mobile'.
// You can assume that no part of a mobile has the same identity as another part.
//
public class Stick implements Mobile // TODO: uncomment clause.
{

    //TODO: define missing parts of the class.
    private final int stickWeight;
    private Mobile[] attached;

    // Initialises 'this'; throws an 'StickBreaksException' if the sum of the weight of
    // all mobiles in the array 'attached' is greater than 10 times the 'hallo'.
    // The detail message of the exception is "Stick breaks!".
    // Precondition: attached.length > 0 and for any two mobiles m1 and m2 being part of
    // 'attached' (including sub-mobiles) it holds that m1.equals(m2) == false, this is,
    // that there are no duplicates anywhere in a mobile.
    public Stick(int stickWeight, Mobile[] attached) throws StickBreaksException {
        this.stickWeight = stickWeight;
        this.attached = attached;

        int totalWeight = 0;
        for (Mobile stars : attached) {
            totalWeight += stars.getWeight();
        }

        if (totalWeight > 10 * stickWeight) {
            throw new StickBreaksException("Stick breaks!");
        }
    }

    // Replaces the mobile equal to 'toReplace' with a new mobile 'replaceWith' if this mobile
    // is directly attached to this stick (no recursive search). In this case 'true' is returned.
    // Otherwise, the call of this method has no effect and 'false' is returned.
    // Throws a 'StickBreaksException' if the replacement would violate the
    // condition that the sum of the weight of all attached mobiles has to be
    // less than or equal to 10 times its stick weight.
    // Precondition: toReplace != null && replaceWith != null
    public boolean replace(Mobile toReplace, Mobile replaceWith) throws StickBreaksException {
        for (int i = 0; i < attached.length; i++) {
            if (attached[i].equals(toReplace)) {
                int totalWeight = 0;
                for (Mobile stars : attached) {
                    totalWeight += stars.getWeight();
                }

                totalWeight -= attached[i].getWeight();
                totalWeight += replaceWith.getWeight();

                if (totalWeight > 10 * stickWeight) {
                    throw new StickBreaksException("Stick breaks!");
                }

                attached[i] = replaceWith;
                return true;
            }
        }
        return false;
    }

    private boolean contains(Star star) {
        ArrayList<Star> stars = asList();
        for (Star value : stars) {
            if (value.equals(star)) {
                return true;
            }
        }
        return false;
    }


    // Returns 'true' if 'o' is also of class 'Stick', has an equal stick weight, and has equal mobiles
    // attached, regardless of their order. This means that 'this' and 'o' have the same number of mobiles
    // attached and every mobile attached to 'this' is equal to a mobile attached to 'o' (and vice versa).
    // Otherwise, 'false' is returned.
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Stick)) return false;
        Stick stars = (Stick) o;
        if (stickWeight != stars.stickWeight) {
            return false;
        }

        if (stars.attached.length != attached.length) {
            return false;
        }

        for (int i = 0; i < stars.attached.length; i++) {
            if (stars.attached[i] instanceof Star star) {
                // if one star is not found in list
                if (!contains(star)) {
                    return false;
                }
                continue;
            }

            if (stars.attached[i] instanceof Stick stick) {
                boolean hasEqualChild = false;
                for (int j = 0; j < attached.length; j++) {
                    if (attached[j].equals(stick)) {
                        hasEqualChild = true;
                        break;
                    }
                }

                if(!hasEqualChild) {
                    return false;
                }
            }
        }
        return true;
    }

    public ArrayList<Star> asList() {
        ArrayList<Star> list = new ArrayList<>();
        addToListRec(list);
        return list;
    }

    public void addToListRec(ArrayList<Star> list) {
        for (int i = 0; i < attached.length; i++) {
            if (attached[i] instanceof Stick stick) {
                stick.addToListRec(list);
            } else if (attached[i] instanceof Star star) {
                list.add(star);
            }
        }
    }

    @Override
    public int getWeight() {
        int weight = stickWeight;
        for (Mobile stars : attached) {
            weight += stars.getWeight();
        }

        return weight;
    }

    @Override
    public Countable getStickCountable() {
        return new StickCountable(attached);
    }

    @Override
    public StarIterator iterator() {
        return new StarIter(asList());
    }

    public String toString() {
        String output = stickWeight + "[";
        for (Mobile stars : attached) {
            output += stars.toString() + ", ";
        }
        output = output.substring(0, output.length() - 2);
        output += "]";

        return output;
    }
}

class StickCountable implements Countable {

    private Mobile[] attached;

    public StickCountable(Mobile[] attached) {
        this.attached = attached;
    }

    @Override
    public int count() {
        int counter = 1;
        for (int i = 0; i < attached.length; i++) {
            if(attached[i] instanceof Stick stick) {
                counter += stick.getStickCountable().count();
            }
        }

        return counter;
    }
}

class StarIter implements StarIterator {
    ArrayList<Star> stars;
    int index;

    public StarIter(ArrayList<Star> stars) {
        this.stars = stars;
    }

    @Override
    public boolean hasNext() {
        return index < stars.size();
    }

    @Override
    public Star next() {
        if (!hasNext()) {
            throw new NoSuchElementException("no star element!");
        }

        Star star = stars.get(index);
        index++;

        return star;
    }
}

// TODO: define additional classes if needed (either here or in a separate file).