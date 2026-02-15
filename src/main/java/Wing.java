import java.util.Scanner;

public class Wing {
    public static void main(String[] args) {
        String logo
                = " __  __  __  ____  __ _  ____   \n"
                + "\\   /   /  /|_  _||  | ||  __|_ \n"
                + " \\   /\\   /  _||_ | || || |__  |\n"
                + "  \\_/  \\_/  |____||_|__||______|\n";

        String bar = "____________________________________________________________\n";

        String StartMsg
                = bar + "Hello! I'm Wing\nWhat can I do for you?\n" + bar;

        System.out.println("Hello from\n" + logo + StartMsg);
        Scanner in = new Scanner(System.in);
        String[] tasks = new String[100];
        int taskCounter = 0;
        while (true) {
            String line = in.nextLine();
            if (line.equals("bye")) {
                System.out.println(bar + "Ok. Bye.\n" + bar);
                break;
            } else if (line.equals("list")) {
                System.out.print(bar);
                for (int i = 0; i < taskCounter; i++) {
                    System.out.println((i + 1) + ": " + tasks[i]);
                }
                System.out.print(bar);
            } else {
                tasks[taskCounter] = line;
                taskCounter++;
                System.out.println(bar + "added: " + line + "\n" + bar);
            }
        }
    }
}
