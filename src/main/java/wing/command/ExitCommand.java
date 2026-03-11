package wing.command;

import wing.storage.Storage;
import wing.tasklist.TaskList;
import wing.ui.Ui;

/**
 * Represents the command that exits the application.
 * Child class of Command class.
 */
public class ExitCommand extends Command {

    /**
     * Overrides parent Command class execute() method.
     * Prints notification that application will terminate.
     *
     * @param tasks   Current TaskList of Tasks.
     * @param ui      UI handler for user interactions.
     * @param storage Storage handler for recording the TaskList.
     */
    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) {
        ui.showBye();
    }

    /**
     * Returns isExit() as true, acting as a flag for main logic to exit application.
     */
    public boolean isExit() {
        return true;
    }

}
