package wing.task;

/**
 * Represents an abstract "task" in the tasklist.
 * Parent abstract class of classes Todo, Deadline and Event.
 */
public abstract class Task {
    protected String description;
    protected boolean isDone;

    /**
     * Constructs a new Task with a description and initialises it as not done (marked).
     *
     * @param description Description of the task.
     */
    public Task(String description) {
        this.description = description;
        this.isDone = false;
    }

    /**
     * Constructs a new Task with a description and whether it is done.
     *
     * @param description Description of the task.
     * @param isDone      True, if the task is marked (done). False, if task is unmarked (not done).
     */
    public Task(String description, boolean isDone) {
        this.description = description;
        this.isDone = isDone;
    }

    /**
     * Returns the description of the task.
     *
     * @return Task's description
     */
    public String getDescription() {
        return description;
    }

    /**
     * Returns the icon signifying if a task is marked (done) or unmarked (not done).
     *
     * @return String of "X" if task isDone, " " if task !isDone.
     */
    public String getStatusIcon() {
        return (isDone ? "X" : " "); // mark done task with X
    }

    /**
     * Sets the isDone boolean member of the task to true (done).
     */
    public void markAsDone() {
        isDone = true;
    }

    /**
     * Sets the isDone boolean member of the task to false (not done).
     */
    public void markAsNotDone() {
        isDone = false;
    }

    /**
     * Overrides the toString method from Object class.
     * Provides a String representation of the Task, including its description and whether it isDone.
     *
     * @return Corresponding String representation of Task.
     */
    @Override
    public String toString() {
        return "[" + this.getStatusIcon() + "] " + description;
    }

}
