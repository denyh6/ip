package wing.ui;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

import wing.task.Task;
import wing.tasklist.TaskList;

public class Ui {

    private static final String DIVIDER
            = "____________________________________________________________" + System.lineSeparator();

    private static final String ERROR_DIVIDER
            = "============================================================" + System.lineSeparator();

    private static final String startMessage
            = """
            This is
              __  __  __  ____  __ _  ____
             \\   /   /  /|_  _||  | ||  __|_
              \\   /\\   /  _||_ | || || |__  |
               \\_/  \\_/  |____||_|__||______|
            """ + DIVIDER + "Hello! I'm wing.Wing."
            + System.lineSeparator() + "What can I do for you?" + System.lineSeparator() + DIVIDER;

    private final Scanner in;

    public Ui() {
        this.in = new Scanner(System.in);
    }

    public void showWelcome() {
        System.out.print(startMessage);
    }

    public String getLine() {
        return in.nextLine();
    }

    public void showBye() {
        System.out.println(DIVIDER + "Ok. Bye." + System.lineSeparator() + DIVIDER);
    }

    public void showAddTask(Task task, int numOfTasks) {
        System.out.println(DIVIDER + "sigh another task. I've added this task: " + System.lineSeparator()
                + " " + task.toString() + System.lineSeparator()
                + "Now there's " + numOfTasks + " task(s) in your list." + System.lineSeparator() + DIVIDER);
    }

    public void showDeleteTask(Task task, int numOfTasks) {
        System.out.println(DIVIDER + "Phew! I've removed this task for you:" + System.lineSeparator()
                + task.toString() + System.lineSeparator()
                + "Now there's " + (numOfTasks - 1) + " task(s) in your list." + System.lineSeparator() + DIVIDER);
    }

    public void showMarkTask(Task task) {
        System.out.println(DIVIDER + "YAY! I've marked this task as done:" + System.lineSeparator()
                + task.toString() + System.lineSeparator() + DIVIDER);
    }

    public void showUnmarkTask(Task task) {
        System.out.println(DIVIDER + "ok sure. This task is unmarked:" + System.lineSeparator()
                + task.toString() + System.lineSeparator() + DIVIDER);
    }

    public void showList(TaskList tasks) {
        if (tasks.size() == 0) {
            System.out.print(DIVIDER + "Your list is empty. YAYYY!" + System.lineSeparator() + DIVIDER);
            return;
        }

        System.out.print(DIVIDER + "Here's your list:" + System.lineSeparator());
        for (int i = 0; i < tasks.size(); i++) {
            System.out.println(" " + (i + 1) + ". " + tasks.get(i).toString());
        }
        System.out.print(DIVIDER);
    }

    public void showError(String error) {
        System.out.println(ERROR_DIVIDER + error + System.lineSeparator() + ERROR_DIVIDER);
    }

    public void showFileContents() throws IOException {
        File file = new File("./data/wing.txt");
        Scanner scanFile = new Scanner(file);
        if (file.length() == 0) {
            System.out.println("You have nothing in your list and wing.txt file. YAYYY!"
                    + System.lineSeparator() + DIVIDER);
            return;
        }

        System.out.println("Here's your previous saved list btw:");
        while (scanFile.hasNext()) {
            System.out.println(" " + scanFile.nextLine());
        }
        System.out.println(DIVIDER);
    }

    public void showFoundTasks(TaskList tasksWithKeyword, String keyword) {
        if (tasksWithKeyword.size() == 0) {
            System.out.println(DIVIDER + "No task descriptions have that word :,(" + System.lineSeparator() + DIVIDER);
            return;
        }

        System.out.print(DIVIDER + "Here are the tasks that have the word '" + keyword + "':" + System.lineSeparator());
        for (int i = 0; i < tasksWithKeyword.size(); i++) {
            System.out.println(" " + (i + 1) + ". " + tasksWithKeyword.get(i).toString());
        }
        System.out.print(DIVIDER);
    }

}
