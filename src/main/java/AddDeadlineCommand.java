public class AddDeadlineCommand extends Command {

    private final String userInput;

    public AddDeadlineCommand(String userInput) {
        this.userInput = userInput;
    }

    private static void addDeadline(TaskList tasks, String line, Ui ui) throws NoByException, NoDescriptionException {
        int byIndex = line.indexOf("/by");
        if (line.equals("deadline")) {
            throw new NoDescriptionException();
        }
        if (byIndex == -1) {
            throw new NoByException();
        }
        String taskToDeadline = line.substring(9, byIndex - 1);
        String by = line.substring(byIndex + 4);
        Deadline newDeadline = new Deadline(taskToDeadline, by);
        tasks.add(newDeadline);
        ui.showAddTask(newDeadline, tasks.size());
    }

    public void execute(TaskList tasks, Ui ui, Storage storage) {
        try {
            addDeadline(tasks, userInput, ui);
            storage.saveTaskList(tasks);
        } catch (NoByException e) {
            ui.showError("EH! Forgot your /by deadline!");
        } catch (NoDescriptionException e) {
            ui.showError("EH! Forgot your description!");
        } catch (Storage.StorageOperationException e) {
            ui.showError("Error with storage save");
        }
    }

}
