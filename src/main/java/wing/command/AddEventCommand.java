package wing.command;

import wing.exception.NoDescriptionException;
import wing.exception.NoFromException;
import wing.exception.NoToException;
import wing.storage.Storage;
import wing.task.Event;
import wing.tasklist.TaskList;
import wing.ui.Ui;

public class AddEventCommand extends Command {

    private final String userInput;

    public AddEventCommand(String userInput) {
        this.userInput = userInput;
    }

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
        } catch (Storage.StorageOperationException e) {
            ui.showError("Error with storage save");
        }
    }

}
