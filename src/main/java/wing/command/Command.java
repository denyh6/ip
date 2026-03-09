package wing.command;

import wing.storage.Storage;
import wing.tasklist.TaskList;
import wing.ui.Ui;

public abstract class Command {

    public abstract void execute(TaskList tasks, Ui ui, Storage storage);

    public boolean isExit() {
        return false;
    }

}
