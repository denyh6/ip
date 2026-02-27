import java.util.Scanner;

public class Wing {
    private static final int MAX_NUMBER_OF_TASKS = 100;
    private static int taskCounter = 0;
    private static final String DIVIDER
            = "____________________________________________________________\n";

    private static void printBye() {
        System.out.println(DIVIDER + "Ok. Bye." + System.lineSeparator() + DIVIDER);
    }

    private static void printList(Task[] tasks) {
        System.out.print(DIVIDER + "Here are the tasks in your list:" + System.lineSeparator());
        for (int i = 0; i < taskCounter; i++) {
            System.out.println((i + 1) + ". " + tasks[i].toString());
        }
        System.out.print(DIVIDER);
    }

    private static void markTask(Task[] tasks, String line) throws NoSuchTaskException {
        int taskToMark = Integer.parseInt(line.substring(5)) - 1;
        if (taskToMark < 0 || taskToMark >= taskCounter) {
            throw new NoSuchTaskException();
        }
        tasks[taskToMark].markAsDone();
        System.out.println(DIVIDER + "YAY! I've marked this task as done:\n  ["
                + tasks[taskToMark].getStatusIcon() + "] "
                + tasks[taskToMark].getDescription() + System.lineSeparator() + DIVIDER);
    }

    private static void unmarkTask(Task[] tasks, String line) throws NoSuchTaskException {
        int taskToUnmark = Integer.parseInt(line.substring(7)) - 1;
        if (taskToUnmark < 0 || taskToUnmark >= taskCounter) {
            throw new NoSuchTaskException();
        }
        tasks[taskToUnmark].markAsNotDone();
        System.out.println(DIVIDER + "oof sure. I've marked this task as not done yet:\n  ["
                + tasks[taskToUnmark].getStatusIcon() + "] "
                + tasks[taskToUnmark].getDescription() + System.lineSeparator() + DIVIDER);
    }

    private static void addTodo(Task[] tasks, String line) throws NoDescriptionException {
        if (line.equals("todo")) {
            throw new NoDescriptionException();
        }
        String taskTodo = line.substring(5);
        tasks[taskCounter] = new Todo(taskTodo);
        System.out.println(DIVIDER + "sigh another todo. I've added this task: " + System.lineSeparator()
                + tasks[taskCounter].toString() + System.lineSeparator()
                + "Now you have " + (taskCounter + 1) + " task(s) in the list." + System.lineSeparator() + DIVIDER);
        taskCounter++;
    }

    private static void addDeadline(Task[] tasks, String line) throws NoByException, NoDescriptionException {
        int byIndex = line.indexOf("/by");
        if (line.equals("deadline")) {
            throw new NoDescriptionException();
        }
        if (byIndex == -1) {
            throw new NoByException();
        }
        String taskToDeadline = line.substring(9, byIndex - 1);
        String by = line.substring(byIndex + 4);
        tasks[taskCounter] = new Deadline(taskToDeadline, by);
        System.out.println(DIVIDER + "yikes another deadline. I've added this task: " + System.lineSeparator()
                + tasks[taskCounter].toString() + System.lineSeparator()
                + "Now you have " + (taskCounter + 1) + " task(s) in the list." + System.lineSeparator() + DIVIDER);
        taskCounter++;
    }

    private static void addEvent(Task[] tasks, String line)
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
        tasks[taskCounter] = new Event(taskToEvent, startToEndDate);
        System.out.println(DIVIDER + "GOSH another event. I've added this task: " + System.lineSeparator()
                + tasks[taskCounter].toString() + System.lineSeparator()
                + "Now you have " + (taskCounter + 1) + " task(s) in the list." + System.lineSeparator() + DIVIDER);
        taskCounter++;
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
        Scanner in = new Scanner(System.in);
        Task[] tasks = new Task[MAX_NUMBER_OF_TASKS];
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
                } catch (NoSuchTaskException e) {
                    System.out.println(DIVIDER + "EH! No such task!" + System.lineSeparator() + DIVIDER);
                }
                break;

            case "unmark":
                try {
                    unmarkTask(tasks, line);
                } catch (NoSuchTaskException e) {
                    System.out.println(DIVIDER + "EH! No such task!" + System.lineSeparator() + DIVIDER);
                }
                break;

            case "todo":
                try {
                    addTodo(tasks, line);
                } catch (NoDescriptionException e) {
                    System.out.println(DIVIDER + "EH! Forgot your description!" + System.lineSeparator() + DIVIDER);
                }
                break;

            case "deadline":
                try {
                    addDeadline(tasks, line);
                } catch (NoByException e) {
                    System.out.println(DIVIDER + "EH! Forgot your /by deadline!" + System.lineSeparator() + DIVIDER);
                } catch (NoDescriptionException e) {
                    System.out.println(DIVIDER + "EH! Forgot your description!" + System.lineSeparator() + DIVIDER);
                }
                break;

            case "event":
                try {
                    addEvent(tasks, line);
                } catch (NoFromException e) {
                    System.out.println(DIVIDER + "EH! Forgot your /from date!" + System.lineSeparator() + DIVIDER);
                } catch (NoToException e) {
                    System.out.println(DIVIDER + "EH! Forgot your /to date!" + System.lineSeparator() + DIVIDER);
                } catch (NoDescriptionException e) {
                    System.out.println(DIVIDER + "EH! Forgot your description!" + System.lineSeparator() + DIVIDER);
                }
                break;

            default:
                System.out.println(DIVIDER + "EH! What talking you?" + System.lineSeparator() + DIVIDER);
                break;
            }
        }
    }
}
