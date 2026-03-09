package wing.command;

import wing.storage.Storage;
import wing.tasklist.TaskList;
import wing.ui.Ui;

public class ListCommand extends Command {

    public void execute(TaskList tasks, Ui ui, Storage storage) {
        ui.showList(tasks);
    }

}
