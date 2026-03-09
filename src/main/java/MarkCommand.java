public class MarkCommand extends Command {

    private final String userInput;

    public MarkCommand(String userInput) {
        this.userInput = userInput;
    }

    private static void markTask(TaskList tasks, String line, Ui ui) throws NoSuchTaskException {
        int taskToMark = Integer.parseInt(line.substring(5)) - 1;
        if (taskToMark < 0 || taskToMark >= tasks.size()) {
            throw new NoSuchTaskException();
        }

        tasks.get(taskToMark).markAsDone();
        ui.showMarkTask(tasks.get(taskToMark));
    }

    public void execute(TaskList tasks, Ui ui, Storage storage) {
        try {
            markTask(tasks, userInput, ui);
            storage.saveTaskList(tasks);
        } catch (NoSuchTaskException e) {
            ui.showError("EH! No such task!");
        } catch (Storage.StorageOperationException e) {
            ui.showError("Error with storage save");
        }
    }

}
