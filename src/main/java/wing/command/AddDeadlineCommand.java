package wing.command;

import wing.exception.NoByException;
import wing.exception.NoDescriptionException;
import wing.exception.StorageOperationException;
import wing.storage.Storage;
import wing.task.Deadline;
import wing.tasklist.TaskList;
import wing.ui.Ui;

/**
 * Represents the command that adds a new Deadline task.
 * Child class of Command class
 */
public class AddDeadlineCommand extends Command {

    private final String userInput;

    /**
     * Constructs an AddDeadlineCommand with user input.
     *
     * @param userInput Details to be made into a Deadline.
     */
    public AddDeadlineCommand(String userInput) {
        this.userInput = userInput;
    }

    /**
     * Constructs a new Deadline using details from user input.
     * Adds created Deadline to current TaskList.
     * Prints notification that it is added.
     *
     * @param tasks Current TaskList of Tasks.
     * @param line User input.
     * @param ui UI handler for user interactions.
     * @throws NoByException If user does not input /by deadline using "/by".
     * @throws NoDescriptionException If user does not input description.
     */
    private static void addDeadline(TaskList tasks, String line, Ui ui) throws NoByException, NoDescriptionException {
        int byIndex = line.indexOf("/by");
        if (line.equals("deadline")) {
            throw new NoDescriptionException();
        }
        if (byIndex == -1) {
            throw new NoByException();
        }

        String taskToDeadline = line.substring(9, byIndex - 1);
        String by = line.substring(byIndex + 4);

        Deadline newDeadline = new Deadline(taskToDeadline, by);
        tasks.add(newDeadline);
        ui.showAddTask(newDeadline, tasks.size());
    }

    /**
     * Overrides parent Command class execute() method.
     * Calls addDeadline method to a new Deadline to current TaskList.
     * Stores updated TaskList to wing.txt.
     *
     * @param tasks Current TaskList of Tasks.
     * @param ui UI handler for user interactions.
     * @param storage Storage handler for recording the TaskList.
     */
    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) {
        try {
            addDeadline(tasks, userInput, ui);
            storage.saveTaskList(tasks);
        } catch (NoByException e) {
            ui.showError("EH! Forgot your /by deadline!");
        } catch (NoDescriptionException e) {
            ui.showError("EH! Forgot your description!");
        } catch (StorageOperationException e) {
            ui.showError("Error with storage save");
        }
    }

}
