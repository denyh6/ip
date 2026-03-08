import java.util.ArrayList;

public class TaskList {

    private final ArrayList<Task> tasks;

    public TaskList() {
        tasks = new ArrayList<>();
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
