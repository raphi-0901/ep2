// A data buffer for a large number of elements (as in the case of large images),
// that are organized into separate arrays (data banks).
public class SimpleDataBufferInt {

    // TODO: make these three variables 'private' and remove 'static'.
    private int numberOfBanks;
    private int bankSize;
    private int[][] data;

    // Initializes 'this' with a specified number of banks, all having the same length
    // given by 'bankSize'.
    // Preconditions: numberOfBanks > 0, bankSize > 0
    public SimpleDataBufferInt(int numberOfBanks, int bankSize) {
        this.numberOfBanks = numberOfBanks;
        this.bankSize = bankSize;
        this.data = new int[numberOfBanks][bankSize];
    }

    public SimpleDataBufferInt(SimpleDataBufferInt simpleDataBufferInt) {
        this.numberOfBanks = simpleDataBufferInt.numberOfBanks;
        this.bankSize = simpleDataBufferInt.bankSize;
        this.data = new int[numberOfBanks][bankSize];

        for (int i = 0; i < data.length; i++) {
            System.arraycopy(simpleDataBufferInt.data[i], 0, data[i], 0, data[i].length);
        }
    }

    // Returns the element with index 'i' of the bank array with index 'bankIndex'.
    // Precondition: all indices are valid (needs not be checked).
    public int getElem(int bankIndex, int i) {
        return data[bankIndex][i];
    }

    // Sets the element with index 'i' of the bank array with index 'bankIndex'.
    // Precondition: all indices are valid (needs not be checked).
    public void setElem(int bankIndex, int i, int value) {
        data[bankIndex][i] = value;
    }
}
