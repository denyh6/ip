import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;
import java.util.ArrayList;
import java.io.File;
import java.io.FileNotFoundException;

public class Wing {

    private static Ui ui;
    private static TaskList tasks;

    private static void markTask(TaskList tasks, String line) throws NoSuchTaskException {
        int taskToMark = Integer.parseInt(line.substring(5)) - 1;
        if (taskToMark < 0 || taskToMark >= tasks.size()) {
            throw new NoSuchTaskException();
        }
        tasks.get(taskToMark).markAsDone();
        ui.showMarkTask(tasks.get(taskToMark));
    }

    private static void unmarkTask(TaskList tasks, String line) throws NoSuchTaskException {
        int taskToUnmark = Integer.parseInt(line.substring(7)) - 1;
        if (taskToUnmark < 0 || taskToUnmark >= tasks.size()) {
            throw new NoSuchTaskException();
        }
        tasks.get(taskToUnmark).markAsNotDone();
        ui.showUnmarkTask(tasks.get(taskToUnmark));
    }

    private static void addTodo(TaskList tasks, String line) throws NoDescriptionException {
        if (line.equals("todo")) {
            throw new NoDescriptionException();
        }
        String taskTodo = line.substring(5);
        Task newTask = new Todo(taskTodo);
        tasks.add(newTask);
        ui.showAddTask(newTask, tasks.size());
    }

    private static void addDeadline(TaskList tasks, String line) throws NoByException, NoDescriptionException {
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

    private static void addEvent(TaskList tasks, String line)
            throws NoFromException, NoToException, NoDescriptionException {
        int startIndex = line.indexOf("/from");
        if (line.equals("event")) {
            throw new NoDescriptionException();
        }
        if (startIndex == -1) {
            throw new NoFromException();
        }
        if (!line.contains("/to")) {
            throw new NoToException();
        }
        String taskToEvent = line.substring(6, startIndex - 1);
        String startToEndDate = line.substring(startIndex);
        Event newEvent = new Event(taskToEvent, startToEndDate);
        tasks.add(newEvent);
        ui.showAddTask(newEvent, tasks.size());
    }

    private static void deleteTask(TaskList tasks, String line) throws NoSuchTaskException {
        int taskToDelete = Integer.parseInt(line.substring(7)) - 1;
        if (taskToDelete < 0 || taskToDelete >= tasks.size()) {
            throw new NoSuchTaskException();
        }
        ui.showDeleteTask(tasks.get(taskToDelete), tasks.size());
        tasks.delete(taskToDelete);
    }

    private static void updateFile(TaskList tasks) throws IOException {
        FileWriter fw = new FileWriter("./data/wing.txt");
        StringBuilder updatedList = new StringBuilder();
        for (Task task : tasks.getTasks()) {
            updatedList.append(task.toString()).append(System.lineSeparator());
        }
        String finalList = updatedList.toString();
        fw.write(finalList);
        fw.close();
    }

    public static void main(String[] args) {

        ui = new Ui();
        ui.showWelcome();
        try {
            ui.showFileContents();
        } catch (FileNotFoundException e) {
            ui.showError("File not found!");
        } catch (IOException e) {
            ui.showError("Error printing: " + e.getMessage());
        }

        tasks = new TaskList();

        while (true) {
            String line = ui.getLine().trim();
            int firstSpaceIndex = line.indexOf(" ");
            String firstWord = (firstSpaceIndex > -1) ? line.substring(0, firstSpaceIndex) : line;

            switch (firstWord) {
            case "bye":
                ui.showBye();
                return;

            case "list":
                ui.showList(tasks);
                break;

            case "mark":
                try {
                    markTask(tasks, line);
                    updateFile(tasks);
                } catch (NoSuchTaskException e) {
                    ui.showError("EH! No such task!");
                } catch (IOException e) {
                    ui.showError("Error updating: " + e.getMessage());
                }
                break;

            case "unmark":
                try {
                    unmarkTask(tasks, line);
                    updateFile(tasks);
                } catch (NoSuchTaskException e) {
                    ui.showError("EH! No such task!");
                } catch (IOException e) {
                    ui.showError("Error updating: " + e.getMessage());
                }
                break;

            case "todo":
                try {
                    addTodo(tasks, line);
                    updateFile(tasks);
                } catch (NoDescriptionException e) {
                    ui.showError("EH! Forgot your description!");
                } catch (IOException e) {
                    ui.showError("Error updating: " + e.getMessage());
                }
                break;

            case "deadline":
                try {
                    addDeadline(tasks, line);
                    updateFile(tasks);
                } catch (NoByException e) {
                    ui.showError("EH! Forgot your /by deadline!");
                } catch (NoDescriptionException e) {
                    ui.showError("EH! Forgot your description!");
                } catch (IOException e) {
                    ui.showError("Error updating: " + e.getMessage());
                }
                break;

            case "event":
                try {
                    addEvent(tasks, line);
                    updateFile(tasks);
                } catch (NoFromException e) {
                    ui.showError("EH! Forgot your /from date!");
                } catch (NoToException e) {
                    ui.showError("EH! Forgot your /to date!");
                } catch (NoDescriptionException e) {
                    ui.showError("EH! Forgot your description!");
                } catch (IOException e) {
                    ui.showError("Error updating: " + e.getMessage());
                }
                break;

            case "delete":
                try {
                    deleteTask(tasks, line);
                    updateFile(tasks);
                } catch (NoSuchTaskException e) {
                    ui.showError("EH! No such task!");
                } catch (IOException e) {
                    ui.showError("Error updating: " + e.getMessage());
                }
                break;

            default:
                ui.showInvalidCommand();
                break;
            }
        }
    }
}
