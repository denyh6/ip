package wing.command;

import wing.exception.WingException;
import wing.storage.Storage;
import wing.task.Task;
import wing.tasklist.TaskList;
import wing.ui.Ui;

/**
 * Represents the command to find Tasks in the current TaskList with a given keyword in its description.
 * Child class of Command class.
 */
public class FindCommand extends Command {

    private final String userInput;

    /**
     * Constructs a FindCommand with user input.
     *
     * @param userInput Details of keyword.
     */
    public FindCommand(String userInput) {
        this.userInput = userInput;
    }

    /**
     * Extracts keyword from user input.
     * Creates new TaskList to contain found Tasks containing the keyword.
     * Iterates through current TaskList, adding Tasks with a description containing the keyword (case-insensitive).
     * Prints TaskList of found Tasks.
     *
     * @param tasks Current TaskList of Tasks.
     * @param line  User input.
     * @param ui    UI handler for user interactions.
     * @throws WingException If user does not input keyword.
     */
    private static void findTask(TaskList tasks, String line, Ui ui) throws WingException {
        if (line.equals("find")) {
            throw new WingException("Eh! Find what?");
        }
        String keyword = line.substring(5);
        TaskList tasksWithKeyword = new TaskList();
        for (Task task : tasks.getTasks()) {
            if (!task.getDescription().toLowerCase().contains(keyword.toLowerCase())) {
                continue;
            }
            tasksWithKeyword.add(task);
        }

        ui.showFoundTasks(tasksWithKeyword, keyword);
    }

    /**
     * Overrides parent Command class execute() method.
     * Calls findTask method to find Tasks with user input keyword.
     *
     * @param tasks   Current TaskList of Tasks.
     * @param ui      UI handler for user interactions.
     * @param storage Storage handler for recording the TaskList.
     */
    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) {
        try {
            findTask(tasks, userInput, ui);
        } catch (WingException e) {
            ui.showError(e.getMessage());
        }
    }
}
