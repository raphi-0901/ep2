import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Map;
import java.util.Scanner;

public class SafeLoadSequenceFactory implements SafeFactory {
    private Map<String, SafeFactory> commandMap;

    // Initializes 'this' with a mapping that associates the string of the command
    // with the corresponding factory.
    // Precondition: commandMap != null.
    public SafeLoadSequenceFactory(Map<String, SafeFactory> commandMap) {
        this.commandMap = commandMap;
    }

    @Override
    // Returns a new 'SafeOperation' from commands stored in a file. The path to the file is
    // provided by the next token of 'sc' (other tokens are ignored).
    // The first word of every line of the file represents a command and the rest of the line
    // provides the parameters required by the corresponding 'SafeFactory' object. All tokens of
    // the line are separated by a blank.
    // The first line of the file has to be a 'create' command.
    // If the file contains exactly one valid line, the method creates a 'SafeOperationSingle'
    // object.
    // If the file contains more than one valid line, the method creates an object of
    // 'SafeOperationSequence'.
    // If the filename is missing or the file can not be found, the file is not in the required
    // format (including the case where the first command is not 'create') or a command is unknown,
    // the method throws a 'FactoryException' using a message string with the information about
    // the cause of the exception.
    public SafeOperation create(Scanner sc) throws FactoryException {
        SafeOperation sequence;
        String path = sc.next();
        try (BufferedReader in = new BufferedReader(new FileReader(path));
        ) {
            // check for create operation
            String line = in.readLine();
            if (line == null) {
                throw new FactoryException();
            }
            Scanner lineScanner = new Scanner(line);
            String command = lineScanner.next();
            if (!command.equals("create")) {
                throw new FactoryException();
            }
            sequence = commandMap.get(command).create(lineScanner);

            // other commands
            line = in.readLine();
            while (line != null) {
                lineScanner = new Scanner(line);
                command = lineScanner.next();

                SafeOperation newOperation = commandMap.get(command).create(lineScanner);
                sequence = new SafeOperationSequence(sequence, newOperation);
                line = in.readLine();
            }
        } catch (Exception e) {
            throw new FactoryException();
        }
        return sequence;
    }
}
