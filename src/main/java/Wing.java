import java.util.Scanner;

public class Wing {
    public static void main(String[] args) {
        String logo
                = """
                 __  __  __  ____  __ _  ____ \s
                \\   /   /  /|_  _||  | ||  __|_
                 \\   /\\   /  _||_ | || || |__  |
                  \\_/  \\_/  |____||_|__||______|
               """;

        String bar
                = "____________________________________________________________\n";

        String StartMsg
                = bar + "Hello! I'm Wing\nWhat can I do for you?\n" + bar;

        System.out.println("Hello from" + System.lineSeparator() + logo + StartMsg);
        Scanner in = new Scanner(System.in);
        Task[] tasks = new Task[100];
        int taskCounter = 0;
        while (true) {
            String line = in.nextLine().trim();
            int firstSpaceIndex = line.indexOf(" ");
            String firstWord = (firstSpaceIndex > -1) ? line.substring(0, firstSpaceIndex) : line;

            switch (firstWord) {
            case "bye":
                System.out.println(bar + "Ok. Bye." + System.lineSeparator() + bar);
                return;

            case "list":
                System.out.print(bar + "Here are the tasks in your list:" + System.lineSeparator());
                for (int i = 0; i < taskCounter; i++) {
                    System.out.println((i + 1) + ". " + tasks[i].toString());
                }
                System.out.print(bar);
                break;

            case "mark":
                int taskToMark = Integer.parseInt(line.substring(5)) - 1;
                tasks[taskToMark].markAsDone();
                System.out.println(bar + "YAY! I've marked this task as done:\n  ["
                        + tasks[taskToMark].getStatusIcon() + "] "
                        + tasks[taskToMark].getDescription() + System.lineSeparator() + bar);
                break;

            case "unmark":
                int taskToUnmark = Integer.parseInt(line.substring(7)) - 1;
                tasks[taskToUnmark].markAsNotDone();
                System.out.println(bar + "oof sure. I've marked this task as not done yet:\n  ["
                        + tasks[taskToUnmark].getStatusIcon() + "] "
                        + tasks[taskToUnmark].getDescription() + System.lineSeparator() + bar);
                break;

            case "todo":
                String taskTodo = line.substring(5);
                tasks[taskCounter] = new Todo(taskTodo);
                System.out.println(bar + "sigh another todo. I've added this task: " + System.lineSeparator()
                        + tasks[taskCounter].toString() + System.lineSeparator()
                        + "Now you have " + (taskCounter + 1) + " task(s) in the list." + System.lineSeparator() + bar);
                taskCounter++;
                break;

            case "deadline":
                int byIndex = line.indexOf("/by");
                String taskToDeadline = line.substring(9, byIndex - 1);
                String by = line.substring(byIndex + 4);
                tasks[taskCounter] = new Deadline(taskToDeadline, by);
                System.out.println(bar + "yikes another deadline. I've added this task: " + System.lineSeparator()
                        + tasks[taskCounter].toString() + System.lineSeparator()
                        + "Now you have " + (taskCounter + 1) + " task(s) in the list." + System.lineSeparator() + bar);
                taskCounter++;
                break;

            case "event":
                int startIndex = line.indexOf("/from");
                String taskToEvent = line.substring(6, startIndex - 1);
                String startToEndDate = line.substring(startIndex);
                tasks[taskCounter] = new Event(taskToEvent, startToEndDate);
                System.out.println(bar + "GOSH another event. I've added this task: " + System.lineSeparator()
                        + tasks[taskCounter].toString() + System.lineSeparator()
                        + "Now you have " + (taskCounter + 1) + " task(s) in the list." + System.lineSeparator() + bar);
                taskCounter++;
                break;
            }
        }
    }
}
