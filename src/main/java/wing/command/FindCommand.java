package wing.command;

import wing.storage.Storage;
import wing.task.Task;
import wing.tasklist.TaskList;
import wing.ui.Ui;

public class FindCommand extends Command {

    private final String userInput;

    public FindCommand(String userInput) {
        this.userInput = userInput;
    }

    private static void findTask(TaskList tasks, String line, Ui ui) {
        String keyword = line.substring(5);
        TaskList tasksWithKeyword = new TaskList();
        for (Task task : tasks.getTasks()) {
            if (!task.getDescription().toLowerCase().contains(keyword.toLowerCase())) {
                continue;
            }
            tasksWithKeyword.add(task);
        }

        ui.showFoundTasks(tasksWithKeyword, keyword);
    }

    public void execute(TaskList tasks, Ui ui, Storage storage) {
        try {
            findTask(tasks, userInput, ui);
            storage.saveTaskList(tasks);
        } catch (Storage.StorageOperationException e) {
            ui.showError("Error with storage save");
        }

    }
}
