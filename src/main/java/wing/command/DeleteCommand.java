package wing.command;

import wing.exception.NoSuchTaskException;
import wing.storage.Storage;
import wing.tasklist.TaskList;
import wing.ui.Ui;

public class DeleteCommand extends Command {

    private final String userInput;

    public DeleteCommand(String userInput) {
        this.userInput = userInput;
    }

    private static void deleteTask(TaskList tasks, String line, Ui ui) throws NoSuchTaskException {
        int taskToDelete = Integer.parseInt(line.substring(7)) - 1;
        if (taskToDelete < 0 || taskToDelete >= tasks.size()) {
            throw new NoSuchTaskException();
        }

        ui.showDeleteTask(tasks.get(taskToDelete), tasks.size());
        tasks.delete(taskToDelete);
    }

    public void execute(TaskList tasks, Ui ui, Storage storage) {
        try {
            deleteTask(tasks, userInput, ui);
            storage.saveTaskList(tasks);
        } catch (NoSuchTaskException e) {
            ui.showError("EH! No such task!");
        } catch (Storage.StorageOperationException e) {
            ui.showError("Error with storage save");
        }
    }

}
