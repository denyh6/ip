package wing.command;

import wing.exception.NoSuchTaskException;
import wing.exception.StorageOperationException;
import wing.exception.WingException;
import wing.storage.Storage;
import wing.tasklist.TaskList;
import wing.ui.Ui;

/**
 * Represents the command to mark the user input Task as done.
 * Child class of Command class.
 */
public class MarkCommand extends Command {

    private final String userInput;

    /**
     * Constructs a MarkCommand with user input.
     *
     * @param userInput Details of index of Task to be marked done.
     */
    public MarkCommand(String userInput) {
        this.userInput = userInput;
    }

    /**
     * Generates index of Task to be marked done using user input.
     * Marks Task in current TaskList as done.
     * Prints notification that it is marked.
     *
     * @param tasks Current TaskList of Tasks.
     * @param line  User input.
     * @param ui    UI handler for user interactions.
     * @throws NoSuchTaskException If a Task with the given index does not exist in the current TaskList.
     * @throws WingException       If the input does not contain an integer representing a Task index.
     */
    private static void markTask(TaskList tasks, String line, Ui ui) throws NoSuchTaskException, WingException {
        if (line.equals("mark")) {
            throw new WingException("Eh! Mark what?");
        }
        int taskToMark;
        try {
            taskToMark = Integer.parseInt(line.substring(5)) - 1;
        } catch (NumberFormatException e) {
            throw new WingException("Give me the task index you wanna mark. For eg, mark 2.");
        }
        if (taskToMark < 0 || taskToMark >= tasks.size()) {
            throw new NoSuchTaskException();
        }

        tasks.get(taskToMark).markAsDone();
        ui.showMarkTask(tasks.get(taskToMark));
    }

    /**
     * Overrides parent Command class execute() method.
     * Calls markTask method to mark intended task as done.
     * Stores updated TaskList to wing.txt.
     *
     * @param tasks   Current TaskList of Tasks.
     * @param ui      UI handler for user interactions.
     * @param storage Storage handler for recording the TaskList.
     */
    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) {
        try {
            markTask(tasks, userInput, ui);
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
