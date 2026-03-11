package wing.command;

import wing.exception.NoSuchTaskException;
import wing.exception.StorageOperationException;
import wing.exception.WingException;
import wing.storage.Storage;
import wing.tasklist.TaskList;
import wing.ui.Ui;

/**
 * Represents the command to unmark the user input Task as not done.
 * Child class of Command class.
 */
public class UnmarkCommand extends Command {

    private final String userInput;

    /**
     * Constructs an UnmarkCommand with user input.
     *
     * @param userInput Details of index of Task to be unmarked (not done).
     */
    public UnmarkCommand(String userInput) {
        this.userInput = userInput;
    }

    /**
     * Generates index of Task to be unmarked using user input.
     * Unmarks Task in current TaskList as not done.
     * Prints notification that it is unmarked.
     *
     * @param tasks Current TaskList of Tasks.
     * @param line User input.
     * @param ui UI handler for user interactions.
     * @throws NoSuchTaskException If a Task with the given index does not exist in the current TaskList.
     * @throws WingException If the input does not contain an integer representing a Task index.
     */
    private static void unmarkTask(TaskList tasks, String line, Ui ui) throws NoSuchTaskException, WingException {
        if (line.equals("unmark")) {
            throw new WingException("Eh! Unmark what?");
        }
        int taskToUnmark;
        try {
            taskToUnmark = Integer.parseInt(line.substring(7)) - 1;
        } catch (NumberFormatException e) {
            throw new WingException("Give me the task index you wanna unmark. For eg, unmark 2.");
        }
        if (taskToUnmark < 0 || taskToUnmark >= tasks.size()) {
            throw new NoSuchTaskException();
        }

        tasks.get(taskToUnmark).markAsNotDone();
        ui.showUnmarkTask(tasks.get(taskToUnmark));
    }

    /**
     * Overrides parent Command class execute() method.
     * Calls unmarkTask method to unmark intended task as not done.
     * Stores updated TaskList to wing.txt.
     *
     * @param tasks Current TaskList of Tasks.
     * @param ui UI handler for user interactions.
     * @param storage Storage handler for recording the TaskList.
     */
    public void execute(TaskList tasks, Ui ui, Storage storage) {
        try {
            unmarkTask(tasks, userInput, ui);
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
