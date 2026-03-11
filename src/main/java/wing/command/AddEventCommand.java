package wing.command;

import wing.exception.NoDescriptionException;
import wing.exception.NoFromException;
import wing.exception.NoToException;
import wing.exception.StorageOperationException;
import wing.storage.Storage;
import wing.task.Event;
import wing.tasklist.TaskList;
import wing.ui.Ui;

/**
 * Represents the command that adds a new Event task.
 * Child class of Command class
 */
public class AddEventCommand extends Command {

    private final String userInput;

    /**
     * Constructs an AddEventCommand with user input.
     *
     * @param userInput Details to be made into an Event.
     */
    public AddEventCommand(String userInput) {
        this.userInput = userInput;
    }

    /**
     * Constructs a new Event using details from user input.
     * Adds created Event to current TaskList.
     * Prints notification that it is added.
     *
     * @param tasks Current TaskList of Tasks.
     * @param line  User input.
     * @param ui    UI handler for user interactions.
     * @throws NoFromException        If user does not input /from start date using "/from".
     * @throws NoToException          If user does not input /to end date using "/to".
     * @throws NoDescriptionException If user does not input description.
     */
    private static void addEvent(TaskList tasks, String line, Ui ui)
            throws NoFromException, NoToException, NoDescriptionException {
        int startIndex = line.indexOf("/from");
        if (line.equals("event")) {
            throw new NoDescriptionException();
        }
        if (startIndex == -1) {
            throw new NoFromException();
        }
        if (!line.contains("/to")) {
            throw new NoToException();
        }

        String taskToEvent = line.substring(6, startIndex - 1);
        String startToEndDate = line.substring(startIndex);

        Event newEvent = new Event(taskToEvent, startToEndDate);
        tasks.add(newEvent);
        ui.showAddTask(newEvent, tasks.size());
    }

    /**
     * Overrides parent Command class execute() method.
     * Calls addEvent method to add new Event to current TaskList.
     * Stores updated TaskList to wing.txt.
     *
     * @param tasks   Current TaskList of Tasks.
     * @param ui      UI handler for user interactions.
     * @param storage Storage handler for recording the TaskList.
     */
    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) {
        try {
            addEvent(tasks, userInput, ui);
            storage.saveTaskList(tasks);
        } catch (NoFromException e) {
            ui.showError("EH! Forgot your /from date!");
        } catch (NoToException e) {
            ui.showError("EH! Forgot your /to date!");
        } catch (NoDescriptionException e) {
            ui.showError("EH! Forgot your description!");
        } catch (StorageOperationException e) {
            ui.showError("Error with storage save");
        }

    }

}
