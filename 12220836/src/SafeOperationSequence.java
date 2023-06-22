import java.util.*;

// Represents a succession of two operations. Each of which can be itself of type
// 'SafeOperationSequence' such that this class represents a recursive (tree-like)
// structure. The foundation (leafs of the tree) is represented by objects of
// 'SafeSingleOperation'.
public class SafeOperationSequence implements SafeOperation, SafeOperationIterable {

    //TODO: define missing parts of this class.
    SafeOperation first;
    SafeOperation second;

    public SafeOperationSequence(SafeOperation first, SafeOperation second) {
        this.first = first;
        this.second = second;
    }

    @Override
    public RasterizedRGB execute(RasterizedRGB raster) throws OperationException {
        if(raster == null) {
            throw new OperationException();
        }

        List<SafeOperationSingle> operationList = operationList();
        RasterizedRGB newRaster = operationList.get(0).execute(raster);
        for (int i = 1; i < operationList.size(); i++) {
            SafeOperationSingle operation = operationList.get(i);
            newRaster = operation.execute(newRaster);
        }
        return newRaster;
    }

    public SafeOperation getFirst() {
        return first;
    }

    public SafeOperation getSecond() {
        return second;
    }

    @Override
    public SafeOperationIterator iterator() {
        return new MySafeOperationIter(operationList());
    }

    private List<SafeOperationSingle> operationList() {
        List<SafeOperationSingle> operationList = new LinkedList<>();
        addToOperationList(this, operationList);
        return operationList;
    }

    private void addToOperationList(SafeOperation operation, List<SafeOperationSingle> list) {
        if(operation instanceof SafeOperationSingle singleOperation) {
            list.add(singleOperation);
            return;
        }

        if(operation instanceof SafeOperationSequence sequence) {
            if(sequence.getFirst() != null) {
                addToOperationList(sequence.getFirst(), list);
            }

            if(sequence.getSecond() != null) {
                addToOperationList(sequence.getSecond(), list);
            }
        }
    }

    @Override
    public String toString() {

        //TODO: implement method.
        return "";
    }
}

class MySafeOperationIter implements SafeOperationIterator {
    private List <SafeOperationSingle> operations;
    private int index;
    public MySafeOperationIter(List<SafeOperationSingle> operations) {
        this.operations = operations;
    }

    @Override
    public SafeOperationSingle next() throws NoSuchElementException {
        if (!hasNext()) {
            throw new NoSuchElementException();
        }

        SafeOperationSingle operation = operations.get(index);
        index++;
        return operation;
    }

    @Override
    public boolean hasNext() {
        return operations.size() > index;
    }
}

//TODO: additional classes for the implementation of the iterator.
