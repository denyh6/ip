package wing.task;

/**
 * Represents a Deadline Task, a Task with a description and a /by deadline.
 * Child class of Task.
 */
public class Deadline extends Task {
    protected String by;

    /**
     * Constructs a new Deadline task with the given description and its deadline.
     * Inherited Task constructor sets isDone to false.
     *
     * @param description Deadline description.
     * @param by Deadline's entire /by String. For example, "/by 4pm".
     */
    public Deadline(String description, String by) {
        super(description);
        this.by = by;
    }

    /**
     * Constructs a new Deadline task with the given description, whether it isDone
     * and its deadline.
     *
     * @param description Deadline description.
     * @param by Deadline's entire /by String. For example, "/by 4pm".
     * @param isDone Boolean whether Deadline isDone.
     */
    public Deadline(String description, String by, boolean isDone) {
        super(description, isDone);
        this.by = by;
    }

    /**
     * Overrides the toString method from parent Task class.
     * Inherits from Task class toString method, which includes its description and whether it isDone.
     * Overridden method now also shows Task is a Deadline with "[D]",
     * and its /by deadline.
     *
     * @return Corresponding String representation of Task.
     */
    @Override
    public String toString() {
        return "[D]" + super.toString() + " (by: " + by + ")";
    }
}
