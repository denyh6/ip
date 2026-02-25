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

    private static void markTask(Task[] tasks, String line){
        int taskToMark = Integer.parseInt(line.substring(5)) - 1;
        tasks[taskToMark].markAsDone();
        System.out.println(DIVIDER + "YAY! I've marked this task as done:\n  ["
                + tasks[taskToMark].getStatusIcon() + "] "
                + tasks[taskToMark].getDescription() + System.lineSeparator() + DIVIDER);
    }

    private static void unmarkTask(Task[] tasks, String line){
        int taskToUnmark = Integer.parseInt(line.substring(7)) - 1;
        tasks[taskToUnmark].markAsNotDone();
        System.out.println(DIVIDER + "oof sure. I've marked this task as not done yet:\n  ["
                + tasks[taskToUnmark].getStatusIcon() + "] "
                + tasks[taskToUnmark].getDescription() + System.lineSeparator() + DIVIDER);
    }

    private static void addTodo(Task[] tasks, String line){
        String taskTodo = line.substring(5);
        tasks[taskCounter] = new Todo(taskTodo);
        System.out.println(DIVIDER + "sigh another todo. I've added this task: " + System.lineSeparator()
                + tasks[taskCounter].toString() + System.lineSeparator()
                + "Now you have " + (taskCounter + 1) + " task(s) in the list." + System.lineSeparator() + DIVIDER);
        taskCounter++;
    }

    private static void addDeadline(Task[] tasks, String line){
        int byIndex = line.indexOf("/by");
        String taskToDeadline = line.substring(9, byIndex - 1);
        String by = line.substring(byIndex + 4);
        tasks[taskCounter] = new Deadline(taskToDeadline, by);
        System.out.println(DIVIDER + "yikes another deadline. I've added this task: " + System.lineSeparator()
                + tasks[taskCounter].toString() + System.lineSeparator()
                + "Now you have " + (taskCounter + 1) + " task(s) in the list." + System.lineSeparator() + DIVIDER);
        taskCounter++;
    }

    private static void addEvent(Task[] tasks, String line){
        int startIndex = line.indexOf("/from");
        String taskToEvent = line.substring(6, startIndex - 1);
        String startToEndDate = line.substring(startIndex);
        tasks[taskCounter] = new Event(taskToEvent, startToEndDate);
        System.out.println(DIVIDER + "GOSH another event. I've added this task: " + System.lineSeparator()
                + tasks[taskCounter].toString() + System.lineSeparator()
                + "Now you have " + (taskCounter + 1) + " task(s) in the list." + System.lineSeparator() + DIVIDER);
        taskCounter++;
    }

    private static void printCommandError(){
        System.out.println(DIVIDER + "What talking you?" + System.lineSeparator() + DIVIDER);
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
                markTask(tasks, line);
                break;

            case "unmark":
                unmarkTask(tasks, line);
                break;

            case "todo":
                addTodo(tasks, line);
                break;

            case "deadline":
                if (line.contains("/by")) {
                    addDeadline(tasks, line);
                } else {
                    printCommandError();
                }
                break;

            case "event":
                if (line.contains("/from") && line.contains("/to")){
                    addEvent(tasks, line);
                } else {
                    printCommandError();
                }
                break;

            default:
                printCommandError();
                break;
            }
        }
    }
}
