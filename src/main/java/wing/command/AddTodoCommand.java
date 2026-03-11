package wing.command;

import wing.exception.NoDescriptionException;
import wing.exception.StorageOperationException;
import wing.storage.Storage;
import wing.task.Task;
import wing.task.Todo;
import wing.tasklist.TaskList;
import wing.ui.Ui;

/**
 * Represents the command that adds a new Todo task.
 * Child class of Command class
 */
public class AddTodoCommand extends Command {

    private final String userInput;

    /**
     * Constructs an AddTodoCommand with user input.
     *
     * @param userInput Details to be made into a Todo.
     */
    public AddTodoCommand(String userInput) {
        this.userInput = userInput;
    }

    /**
     * Constructs a new Todo using details from user input.
     * Adds created Todo to current TaskList.
     * Prints notification that it is added.
     *
     * @param tasks Current TaskList of Tasks.
     * @param line User input.
     * @param ui UI handler for user interactions.
     * @throws NoDescriptionException If user does not input description.
     */
    private static void addTodo(TaskList tasks, String line, Ui ui) throws NoDescriptionException {
        if (line.equals("todo")) {
            throw new NoDescriptionException();
        }

        String taskTodo = line.substring(5);

        Task newTask = new Todo(taskTodo);
        tasks.add(newTask);
        ui.showAddTask(newTask, tasks.size());
    }

    /**
     * Overrides parent Command class execute() method.
     * Calls addTodo method to add new Todo to current TaskList.
     * Stores updated TaskList to wing.txt.
     *
     * @param tasks Current TaskList of Tasks.
     * @param ui UI handler for user interactions.
     * @param storage Storage handler for recording the TaskList.
     */
    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) {
        try {
            addTodo(tasks, userInput, ui);
            storage.saveTaskList(tasks);
        } catch (NoDescriptionException e) {
            ui.showError("EH! Forgot your description!");
        } catch (StorageOperationException e) {
            ui.showError("Error with storage save");
        }
    }

}
