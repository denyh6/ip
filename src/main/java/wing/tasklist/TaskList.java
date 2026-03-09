package wing.tasklist;

import java.util.ArrayList;

import wing.task.Task;

public class TaskList {

    private final ArrayList<Task> tasks;

    public TaskList() {
        tasks = new ArrayList<>();
    }

    public TaskList(ArrayList<Task> tasks) {
        this.tasks = tasks;
    }

    public void add(Task task) {
        tasks.add(task);
    }

    public void delete(int taskIndex) {
        tasks.remove(taskIndex);
    }

    public Task get(int taskIndex) {
        return tasks.get(taskIndex);
    }

    public ArrayList<Task> getTasks() {
        return tasks;
    }

    public int size() {
        return tasks.size();
    }

}
