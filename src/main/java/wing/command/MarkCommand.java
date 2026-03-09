package wing.command;

import wing.exception.NoSuchTaskException;
import wing.exception.WingException;
import wing.storage.Storage;
import wing.tasklist.TaskList;
import wing.ui.Ui;

public class MarkCommand extends Command {

    private final String userInput;

    public MarkCommand(String userInput) {
        this.userInput = userInput;
    }

    private static void markTask(TaskList tasks, String line, Ui ui) throws NoSuchTaskException, WingException {
        int taskToMark;
        try {
            taskToMark = Integer.parseInt(line.substring(5)) - 1;
        } catch (NumberFormatException e) {
            throw new WingException("Give me the task's index you wanna mark. For eg, mark 2.");
        }
        if (taskToMark < 0 || taskToMark >= tasks.size()) {
            throw new NoSuchTaskException();
        }

        tasks.get(taskToMark).markAsDone();
        ui.showMarkTask(tasks.get(taskToMark));
    }

    public void execute(TaskList tasks, Ui ui, Storage storage) {
        try {
            markTask(tasks, userInput, ui);
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
