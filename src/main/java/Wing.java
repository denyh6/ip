import java.util.Scanner;

public class Wing {
    public static void main(String[] args) {
        String logo
                = " __  __  __  ____  __ _  ____   \n"
                + "\\   /   /  /|_  _||  | ||  __|_ \n"
                + " \\   /\\   /  _||_ | || || |__  |\n"
                + "  \\_/  \\_/  |____||_|__||______|\n";

        String bar
                = "____________________________________________________________\n";

        String StartMsg
                = bar + "Hello! I'm Wing\nWhat can I do for you?\n" + bar;

        System.out.println("Hello from\n" + logo + StartMsg);
        Scanner in = new Scanner(System.in);
        Task[] tasks = new Task[100];
        int taskCounter = 0;
        while (true) {
            String line = in.nextLine();
            if (line.equals("bye")) {
                System.out.println(bar + "Ok. Bye.\n" + bar);
                break;
            } else if (line.equals("list")) {
                System.out.print(bar + "Here are the tasks in your list:\n");
                for (int i = 0; i < taskCounter; i++) {
                    System.out.println((i + 1) + ".[" + tasks[i].getStatusIcon() + "] " + tasks[i].getDescription());
                }
                System.out.print(bar);
            } else if (line.startsWith("mark")) {
                tasks[Integer.parseInt(line.substring(5)) - 1].markAsDone();
                System.out.println(bar + "YAY! I've marked this task as done:\n  ["
                        + tasks[Integer.parseInt(line.substring(5)) - 1].getStatusIcon() + "] "
                        + tasks[Integer.parseInt(line.substring(5)) - 1].getDescription() + "\n" + bar);
            } else if (line.startsWith("unmark")) {
                tasks[Integer.parseInt(line.substring(7)) - 1].markAsNotDone();
                System.out.println(bar + "oof sure. I've marked this task as not done yet:\n  ["
                        + tasks[Integer.parseInt(line.substring(7)) - 1].getStatusIcon() + "] "
                        + tasks[Integer.parseInt(line.substring(7)) - 1].getDescription() + "\n" + bar);
            } else {
                tasks[taskCounter] = new Task(line);
                System.out.println(bar + "added: " + tasks[taskCounter].getDescription() + "\n" + bar);
                taskCounter++;
            }
        }
    }
}
