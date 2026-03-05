import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;
import java.util.ArrayList;
import java.io.File;
import java.io.FileNotFoundException;

public class Wing {
    private static final int MAX_NUMBER_OF_TASKS = 100;
    private static int taskCounter = 0;
    private static final String DIVIDER
            = "____________________________________________________________\n";

    private static void printBye() {
        System.out.println(DIVIDER + "Ok. Bye." + System.lineSeparator() + DIVIDER);
    }

    private static void printList(ArrayList<Task> tasks) {
        System.out.print(DIVIDER + "Here's your list:" + System.lineSeparator());
        for (int i = 0; i < tasks.size(); i++) {
            System.out.println((i + 1) + ". " + tasks.get(i).toString());
        }
        System.out.print(DIVIDER);
    }

    private static void markTask(ArrayList<Task> tasks, String line) throws NoSuchTaskException {
        int taskToMark = Integer.parseInt(line.substring(5)) - 1;
        if (taskToMark < 0 || taskToMark >= tasks.size()) {
            throw new NoSuchTaskException();
        }
        tasks.get(taskToMark).markAsDone();
        System.out.println(DIVIDER + "YAY! I've marked this task as done:" + System.lineSeparator()
                + tasks.get(taskToMark).toString() + System.lineSeparator() + DIVIDER);
    }

    private static void unmarkTask(ArrayList<Task> tasks, String line) throws NoSuchTaskException {
        int taskToUnmark = Integer.parseInt(line.substring(7)) - 1;
        if (taskToUnmark < 0 || taskToUnmark >= tasks.size()) {
            throw new NoSuchTaskException();
        }
        tasks.get(taskToUnmark).markAsNotDone();
        System.out.println(DIVIDER + "oof sure. I've marked this task as not done yet:" + System.lineSeparator()
                + tasks.get(taskToUnmark).toString() + System.lineSeparator() + DIVIDER);
    }

    private static void addTodo(ArrayList<Task> tasks, String line) throws NoDescriptionException {
        if (line.equals("todo")) {
            throw new NoDescriptionException();
        }
        String taskTodo = line.substring(5);
        Task newTask = new Todo(taskTodo);
        tasks.add(newTask);
        System.out.println(DIVIDER + "sigh another todo. I've added this task: " + System.lineSeparator()
                + tasks.get(tasks.indexOf(newTask)).toString() + System.lineSeparator()
                + "Now there's " + tasks.size() + " task(s) in your list." + System.lineSeparator() + DIVIDER);
    }

    private static void addDeadline(ArrayList<Task> tasks, String line) throws NoByException, NoDescriptionException {
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
        System.out.println(DIVIDER + "yikes another deadline. I've added this task: " + System.lineSeparator()
                + tasks.get(tasks.indexOf(newDeadline)).toString() + System.lineSeparator()
                + "Now there's " + tasks.size() + " task(s) in your list." + System.lineSeparator() + DIVIDER);
    }

    private static void addEvent(ArrayList<Task> tasks, String line)
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
        System.out.println(DIVIDER + "GOSH another event. I've added this task: " + System.lineSeparator()
                + tasks.get(tasks.indexOf(newEvent)).toString() + System.lineSeparator()
                + "Now there's " + tasks.size() + " task(s) in your list." + System.lineSeparator() + DIVIDER);
    }

    private static void deleteTask(ArrayList<Task> tasks, String line) throws NoSuchTaskException {
        int taskToDelete = Integer.parseInt(line.substring(7)) - 1;
        if (taskToDelete < 0 || taskToDelete >= tasks.size()) {
            throw new NoSuchTaskException();
        }
        System.out.println(DIVIDER + "Phew! I've removed this task for you:" + System.lineSeparator()
                + tasks.get(taskToDelete).toString() + System.lineSeparator()
                + "Now there's " + (tasks.size() - 1) + " task(s) in your list." + System.lineSeparator() + DIVIDER);
        tasks.remove(taskToDelete);
    }

    private static void printFileContents() throws IOException {
        File dir = new File("./data");
        if (!dir.exists()) {
            dir.mkdir();
        }
        File file = new File("./data/wing.txt");
        if (!file.exists()) {
            file.createNewFile();
            return;
        }
        Scanner scanFile = new Scanner(file);
        System.out.println("Here's your previous saved list btw");
        while (scanFile.hasNext()) {
            System.out.println(scanFile.nextLine());
        }
        System.out.println(System.lineSeparator());
    }

    private static void updateFile(ArrayList<Task> tasks) throws IOException {
        FileWriter fw = new FileWriter("./data/wing.txt");
        StringBuilder updatedList = new StringBuilder();
        for (Task task : tasks) {
            updatedList.append(task.toString()).append(System.lineSeparator());
        }
        String finalList = updatedList.toString();
        fw.write(finalList);
        fw.close();
    }

    public static void main(String[] args) {
        String startMessage
                = """
                This is
                  __  __  __  ____  __ _  ____
                 \\   /   /  /|_  _||  | ||  __|_
                  \\   /\\   /  _||_ | || || |__  |
                   \\_/  \\_/  |____||_|__||______|
                """ + DIVIDER + "Hello! I'm Wing."
                + System.lineSeparator() + "What can I do for you?" + System.lineSeparator() + DIVIDER;

        System.out.println(startMessage);
        try {
            printFileContents();
        } catch (FileNotFoundException e) {
            System.out.println("File not found!");
        } catch (IOException e) {
            System.out.println("Error printing: " + e.getMessage());
        }
        Scanner in = new Scanner(System.in);
        ArrayList<Task> tasks = new ArrayList<>();
        while (true) {
            String line = in.nextLine().trim();
            int firstSpaceIndex = line.indexOf(" ");
            String firstWord = (firstSpaceIndex > -1) ? line.substring(0, firstSpaceIndex) : line;

            switch (firstWord) {
            case "bye":
                printBye();
                return;

            case "list":
                printList(tasks);
                break;

            case "mark":
                try {
                    markTask(tasks, line);
                    updateFile(tasks);
                } catch (NoSuchTaskException e) {
                    System.out.println(DIVIDER + "EH! No such task!" + System.lineSeparator() + DIVIDER);
                } catch (IOException e) {
                    System.out.println("Error updating: " + e.getMessage());
                }
                break;

            case "unmark":
                try {
                    unmarkTask(tasks, line);
                    updateFile(tasks);
                } catch (NoSuchTaskException e) {
                    System.out.println(DIVIDER + "EH! No such task!" + System.lineSeparator() + DIVIDER);
                } catch (IOException e) {
                    System.out.println("Error updating: " + e.getMessage());
                }
                break;

            case "todo":
                try {
                    addTodo(tasks, line);
                    updateFile(tasks);
                } catch (NoDescriptionException e) {
                    System.out.println(DIVIDER + "EH! Forgot your description!" + System.lineSeparator() + DIVIDER);
                } catch (IOException e) {
                    System.out.println("Error updating: " + e.getMessage());
                }
                break;

            case "deadline":
                try {
                    addDeadline(tasks, line);
                    updateFile(tasks);
                } catch (NoByException e) {
                    System.out.println(DIVIDER + "EH! Forgot your /by deadline!" + System.lineSeparator() + DIVIDER);
                } catch (NoDescriptionException e) {
                    System.out.println(DIVIDER + "EH! Forgot your description!" + System.lineSeparator() + DIVIDER);
                } catch (IOException e) {
                    System.out.println("Error updating: " + e.getMessage());
                }
                break;

            case "event":
                try {
                    addEvent(tasks, line);
                    updateFile(tasks);
                } catch (NoFromException e) {
                    System.out.println(DIVIDER + "EH! Forgot your /from date!" + System.lineSeparator() + DIVIDER);
                } catch (NoToException e) {
                    System.out.println(DIVIDER + "EH! Forgot your /to date!" + System.lineSeparator() + DIVIDER);
                } catch (NoDescriptionException e) {
                    System.out.println(DIVIDER + "EH! Forgot your description!" + System.lineSeparator() + DIVIDER);
                } catch (IOException e) {
                    System.out.println("Error updating: " + e.getMessage());
                }
                break;

            case "delete":
                try {
                    deleteTask(tasks, line);
                    updateFile(tasks);
                } catch (NoSuchTaskException e) {
                    System.out.println(DIVIDER + "EH! No such task!" + System.lineSeparator() + DIVIDER);
                } catch (IOException e) {
                    System.out.println("Error updating: " + e.getMessage());
                }
                break;

            default:
                System.out.println(DIVIDER + "EH! What talking you?" + System.lineSeparator() + DIVIDER);
                break;
            }
        }
    }
}
