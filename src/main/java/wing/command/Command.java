package wing.command;

import wing.storage.Storage;
import wing.tasklist.TaskList;
import wing.ui.Ui;

/**
 * Represents an executable command.
 * Parent abstract class for all commands.
 */
public abstract class Command {

    /**
     * Executes the command and returns the result.
     *
     * @param tasks   Current TaskList of Tasks.
     * @param ui      UI handler for user interactions.
     * @param storage Storage handler for recording the TaskList.
     */
    public abstract void execute(TaskList tasks, Ui ui, Storage storage);

    /**
     * Returns isExit() as false, allowing for main logic to continue.
     * If set to true, acts as a flag for main logic to exit application.
     *
     * @return {@code true} if the app should exit, {@code false} continue.
     */
    public boolean isExit() {
        return false;
    }

}
