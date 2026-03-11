package wing.ui;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import wing.task.Task;
import wing.tasklist.TaskList;

/**
 * Text UI of the application.
 */
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
            """ + DIVIDER + "Hello! I'm wing. This is the Branch-Level-6 Branch!"
            + System.lineSeparator() + "What can I do for you?" + System.lineSeparator() + DIVIDER;

    private final Scanner in;

    /**
     * Constructs an Ui instance and initialises the input scanner.
     */
    public Ui() {
        this.in = new Scanner(System.in);
    }

    /**
     * Prints the welcome message upon the start of the application
     */
    public void showWelcome() {
        System.out.print(startMessage);
    }

    /**
     * Reads in user input.
     *
     * @return User input as a string.
     */
    public String getLine() {
        return in.nextLine();
    }

    /**
     * Prints the goodbye message for when user gives exit command
     */
    public void showBye() {
        System.out.println(DIVIDER + "Ok. Bye." + System.lineSeparator() + DIVIDER);
    }

    /**
     * Prints notification that given task was added to main TaskList.
     *
     * @param task       Task that was added.
     * @param numOfTasks Total number of tasks in TaskList, after adding the given task.
     */
    public void showAddTask(Task task, int numOfTasks) {
        System.out.println(DIVIDER + "sigh another task. I've added this task: " + System.lineSeparator()
                + " " + task.toString() + System.lineSeparator()
                + "Now there's " + numOfTasks + " task(s) in your list." + System.lineSeparator() + DIVIDER);
    }

    /**
     * Prints notification that a task was removed from main TaskList.
     *
     * @param task       Task that was removed.
     * @param numOfTasks Total number of tasks in TaskList, after removing the given task.
     */
    public void showDeleteTask(Task task, int numOfTasks) {
        System.out.println(DIVIDER + "Phew! I've removed this task for you:" + System.lineSeparator()
                + task.toString() + System.lineSeparator()
                + "Now there's " + (numOfTasks - 1) + " task(s) in your list." + System.lineSeparator() + DIVIDER);
    }

    /**
     * Prints notification that a task was marked.
     *
     * @param task Task that was marked.
     */
    public void showMarkTask(Task task) {
        System.out.println(DIVIDER + "YAY! I've marked this task as done:" + System.lineSeparator()
                + task.toString() + System.lineSeparator() + DIVIDER);
    }

    /**
     * Prints notification that a task was unmarked.
     *
     * @param task Task that was unmarked.
     */
    public void showUnmarkTask(Task task) {
        System.out.println(DIVIDER + "ok sure. This task is unmarked:" + System.lineSeparator()
                + task.toString() + System.lineSeparator() + DIVIDER);
    }

    /**
     * Shows a list of tasks to the user, formatted as an indexed list.
     * Calls each Task's toString method and prints Tasks' Descriptions and by, from, to deadlines.
     *
     * @param tasks Current TaskList to be printed.
     */
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

    /**
     * Prints out an error message to the user.
     *
     * @param error Error message to be printed.
     */
    public void showError(String error) {
        System.out.println(ERROR_DIVIDER + error + System.lineSeparator() + ERROR_DIVIDER);
    }

    /**
     * Prints the data in the wing.txt file.
     *
     * @throws FileNotFoundException If wing.txt file is not found.
     */
    public void showFileContents() throws FileNotFoundException {
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

    /**
     * Prints a list of tasks that contain the given keyword.
     *
     * @param tasksWithKeyword TaskList containing tasks with the keyword in its description.
     * @param keyword          keyword input by the user to be found.
     */
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
