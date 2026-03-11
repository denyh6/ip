package wing.command;

import wing.storage.Storage;
import wing.tasklist.TaskList;
import wing.ui.Ui;

/**
 * Represents the command to print the current TaskList.
 * Child class of Command class.
 */
public class ListCommand extends Command {

    /**
     * Overrides parent Command class execute() method.
     * Prints current TaskList.
     *
     * @param tasks   Current TaskList of Tasks.
     * @param ui      UI handler for user interactions.
     * @param storage Storage handler for recording the TaskList.
     */
    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) {
        ui.showList(tasks);
    }

}
