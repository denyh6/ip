package wing.command;

import wing.exception.NoSuchTaskException;
import wing.exception.StorageOperationException;
import wing.exception.WingException;
import wing.storage.Storage;
import wing.tasklist.TaskList;
import wing.ui.Ui;

/**
 * Represents the command that removes a task by index from current TaskList.
 * Child class of Command class.
 */
public class DeleteCommand extends Command {

    private final String userInput;

    /**
     * Constructs a DeleteCommand with user input.
     *
     * @param userInput Details of Task to be removed.
     */
    public DeleteCommand(String userInput) {
        this.userInput = userInput;
    }

    /**
     * Generates index of Task to be removed using user input.
     * Prints notification that it is removed.
     * Removes Task from current TaskList.
     *
     * @param tasks Current TaskList of Tasks.
     * @param line  User input.
     * @param ui    UI handler for user interactions.
     * @throws NoSuchTaskException If a Task with the given index does not exist in the current TaskList.
     * @throws WingException       If user does not input index of Task to remove.
     */
    private static void deleteTask(TaskList tasks, String line, Ui ui) throws NoSuchTaskException, WingException {
        if (line.equals("delete")) {
            throw new WingException("Eh! Delete what?");
        }
        int taskToDelete;
        try {
            taskToDelete = Integer.parseInt(line.substring(7)) - 1;
        } catch (NumberFormatException e) {
            throw new WingException("Give me the task index you wanna delete. For eg, delete 2.");
        }
        if (taskToDelete < 0 || taskToDelete >= tasks.size()) {
            throw new NoSuchTaskException();
        }

        ui.showDeleteTask(tasks.get(taskToDelete), tasks.size());
        tasks.delete(taskToDelete);
    }

    /**
     * Overrides parent Command class execute() method.
     * Calls deleteTask method to remove given task from current TaskList.
     * Stores updated TaskList to wing.txt.
     *
     * @param tasks   Current TaskList of Tasks.
     * @param ui      UI handler for user interactions.
     * @param storage Storage handler for recording the TaskList.
     */
    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) {
        try {
            deleteTask(tasks, userInput, ui);
            storage.saveTaskList(tasks);
        } catch (NoSuchTaskException e) {
            ui.showError("EH! No such task!");
        } catch (StorageOperationException e) {
            ui.showError("Error with storage save");
        } catch (WingException e) {
            ui.showError(e.getMessage());
        }

    }

}
