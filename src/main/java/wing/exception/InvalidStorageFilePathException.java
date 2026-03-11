package wing.exception;

/**
 * Signals that the given file path does not fulfill the storage filepath constraints.
 */
public class InvalidStorageFilePathException extends Exception {
    /**
     * @param message should contain relevant information on the failed constraint
     */
    public InvalidStorageFilePathException(String message) {
        super(message);
    }
}
