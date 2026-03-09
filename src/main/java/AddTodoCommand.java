public class AddTodoCommand extends Command {

    private final String userInput;

    public AddTodoCommand(String userInput) {
        this.userInput = userInput;
    }

    private static void addTodo(TaskList tasks, String line, Ui ui) throws NoDescriptionException {
        if (line.equals("todo")) {
            throw new NoDescriptionException();
        }

        String taskTodo = line.substring(5);

        Task newTask = new Todo(taskTodo);
        tasks.add(newTask);
        ui.showAddTask(newTask, tasks.size());
    }

    public void execute(TaskList tasks, Ui ui, Storage storage) {
        try {
            addTodo(tasks, userInput, ui);
            storage.saveTaskList(tasks);
        } catch (NoDescriptionException e) {
            ui.showError("EH! Forgot your description!");
        } catch (Storage.StorageOperationException e) {
            ui.showError("Error with storage save");
        }
    }

}
