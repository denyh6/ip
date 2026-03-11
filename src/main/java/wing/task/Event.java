package wing.task;

/**
 * Represents an Event Task, a Task with a description and a start to end date range.
 * Child class of Task.
 */
public class Event extends Task {
    protected String startToEndDate;

    /**
     * Constructs a new Event task with the given description and its start to end date range.
     * Inherited Task constructor sets isDone to false.
     *
     * @param description Event description.
     * @param startToEndDate Event's entire /from to /to String. For example, "/from 3pm /to 4pm".
     */
    public Event(String description, String startToEndDate) {
        super(description);
        this.startToEndDate = startToEndDate;
    }

    /**
     * Constructs a new Event task with the given description, whether it isDone
     * and its start to end date range.
     *
     * @param description Event description.
     * @param startToEndDate Event's entire /from to /to String. For example, "/from 3pm /to 4pm".
     * @param isDone Boolean whether Event isDone.
     */
    public Event(String description, String startToEndDate, boolean isDone) {
        super(description, isDone);
        this.startToEndDate = startToEndDate;
    }

    /**
     * Overrides the toString method from parent Task class.
     * Inherits from Task class toString method, which includes its description and whether it isDone.
     * Overridden method now also shows Task is an Event with "[E]",
     * and its /from (start) and /to (end) range.
     *
     * @return Corresponding String representation of Task.
     */
    @Override
    public String toString() {
        return "[E]" + super.toString() + " (" + startToEndDate.replace("/from", "from:")
                .replace("/to", "to:") + ")";
    }
}
