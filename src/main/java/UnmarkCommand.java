public class UnmarkCommand extends Command {

    private final String userInput;

    public UnmarkCommand(String userInput) {
        this.userInput = userInput;
    }

    private static void unmarkTask(TaskList tasks, String line, Ui ui) throws NoSuchTaskException {
        int taskToUnmark = Integer.parseInt(line.substring(7)) - 1;
        if (taskToUnmark < 0 || taskToUnmark >= tasks.size()) {
            throw new NoSuchTaskException();
        }

        tasks.get(taskToUnmark).markAsNotDone();
        ui.showUnmarkTask(tasks.get(taskToUnmark));
    }

    public void execute(TaskList tasks, Ui ui, Storage storage) {
        try {
            unmarkTask(tasks, userInput, ui);
            storage.saveTaskList(tasks);
        } catch (NoSuchTaskException e) {
            ui.showError("EH! No such task!");
        } catch (Storage.StorageOperationException e) {
            ui.showError("Error with storage save");
        }
    }

}
