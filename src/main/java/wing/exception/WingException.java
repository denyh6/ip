package wing.exception;

/**
 * Signals that some given data does not fulfill some constraints.
 */
public class WingException extends Exception {
    /**
     * @param message should contain relevant information on the failed constraint(s)
     */
    public WingException(String message) {
        super(message);
    }
}
