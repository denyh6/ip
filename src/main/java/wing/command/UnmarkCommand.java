package wing.command;

import wing.exception.NoSuchTaskException;
import wing.exception.WingException;
import wing.storage.Storage;
import wing.tasklist.TaskList;
import wing.ui.Ui;

public class UnmarkCommand extends Command {

    private final String userInput;

    public UnmarkCommand(String userInput) {
        this.userInput = userInput;
    }

    private static void unmarkTask(TaskList tasks, String line, Ui ui) throws NoSuchTaskException, WingException {
        int taskToUnmark;
        try {
            taskToUnmark = Integer.parseInt(line.substring(7)) - 1;
        } catch (NumberFormatException e) {
            throw new WingException("Give me the task's index you wanna unmark. For eg, unmark 2.");
        }
        if (taskToUnmark < 0 || taskToUnmark >= tasks.size()) {
            throw new NoSuchTaskException();
        }

        tasks.get(taskToUnmark).markAsNotDone();
        ui.showUnmarkTask(tasks.get(taskToUnmark));
    }

    public void execute(TaskList tasks, Ui ui, Storage storage) {
        try {
            unmarkTask(tasks, userInput, ui);
            storage.saveTaskList(tasks);
        } catch (NoSuchTaskException e) {
            ui.showError("EH! No such task!");
        } catch (Storage.StorageOperationException e) {
            ui.showError("Error with storage save");
        } catch (WingException e) {
            ui.showError(e.getMessage());
        }
    }

}
