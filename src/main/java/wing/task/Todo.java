package wing.task;

/**
 * Represents a Todo Task, a Task with only a description.
 * Child class of Task.
 */
public class Todo extends Task {

    /**
     * Constructs a new Todo task with the given description.
     * Inherited Task constructor sets isDone to false.
     *
     * @param description Todo description.
     */
    public Todo(String description) {
        super(description);
    }

    /**
     * Constructs a new Todo task with the given description and whether it isDone.
     *
     * @param description Todo description.
     * @param isDone Boolean whether Todo isDone.
     */
    public Todo(String description, boolean isDone) {
        super(description, isDone);
    }

    /**
     * Overrides the toString method from parent Task class.
     * Inherits from Task class toString method, which includes its description and whether it isDone.
     * Overridden method now also shows Task is a Todo with "[T]".
     *
     * @return Corresponding String representation of Task.
     */
    @Override
    public String toString() {
        return "[T]" + super.toString();
    }
}
