package wing.exception;

/**
 * Signals that some error has occurred while trying to convert and read/write data
 * between the application and the storage file.
 */
public class StorageOperationException extends Exception {
    /**
     * @param message should contain relevant information on the failed constraint
     */
    public StorageOperationException(String message) {
        super(message);
    }
}
