package wing.tasklist;

import java.util.ArrayList;

import wing.task.Task;

/**
 * Represents the entire tasklist. Contains the data of the tasklist.
 */
public class TaskList {

    private final ArrayList<Task> tasks;

    /**
     * Creates an empty tasklist.
     */
    public TaskList() {
        tasks = new ArrayList<>();
    }

    /**
     * Constructs a tasklist with the given ArrayList of tasks.
     *
     * @param tasks Arraylist of tasks to be made into a TaskList.
     */
    public TaskList(ArrayList<Task> tasks) {
        this.tasks = tasks;
    }

    /**
     * Adds a task to the TaskList.
     *
     * @param task Task to be added.
     */
    public void add(Task task) {
        tasks.add(task);
    }

    /**
     * Removes the task from the TaskList.
     *
     * @param taskIndex Index of the Task to be removed.
     */
    public void delete(int taskIndex) {
        tasks.remove(taskIndex);
    }

    /**
     * Returns a specific Task within the TaskList.
     *
     * @param taskIndex Index of the task to be referenced or updated.
     * @return Specific Task
     */
    public Task get(int taskIndex) {
        return tasks.get(taskIndex);
    }

    /**
     * Returns the ArrayList of the TaskList.
     */
    public ArrayList<Task> getTasks() {
        return tasks;
    }

    /**
     * Returns the number of tasks in the TaskList.
     */
    public int size() {
        return tasks.size();
    }

}
