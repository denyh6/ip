package wing;

import java.io.FileNotFoundException;
import java.io.IOException;

import wing.command.Command;
import wing.exception.InvalidStorageFilePathException;
import wing.exception.WingException;
import wing.parser.Parser;
import wing.storage.Storage;
import wing.tasklist.TaskList;
import wing.ui.Ui;

/**
 * Entry point of the Wing application.
 * Initialises the application and starts the interaction with the user.
 */
public class Wing {

    private final Storage storage;
    private TaskList tasks;
    private final Ui ui;

    /**
     * Sets up key classes Storage, TaskList and Ui.
     * Loads saved tasks from wing.txt. If it does not exist, will create it.
     *
     * @param filePath The file path of the data file wing.txt, containing saved tasks from previous Wing instance.
     * @throws InvalidStorageFilePathException If
     */
    public Wing(String filePath) throws InvalidStorageFilePathException {
        ui = new Ui();
        storage = new Storage(filePath);
        try {
            tasks = new TaskList(storage.loadTaskList());
        } catch (IOException e) {
            ui.showError("File not found! Don't worry, I'll create one!");
            tasks = new TaskList();
        }
    }

    /**
     * Runs the main logic loop of the application, reading, understanding, then executing user commands.
     * Loop continues until the exit command is given.
     *
     * @throws FileNotFoundException If ui.showFileContents() cannot find wing.txt.
     */
    public void run() throws IOException {
        ui.showWelcome();
        ui.showFileContents(storage.loadTaskList());
        boolean isExit = false;
        while (!isExit) {
            try {
                String userInput = ui.getLine().trim();
                Command c = Parser.parse(userInput);
                c.execute(tasks, ui, storage);
                isExit = c.isExit();
            } catch (WingException e) {
                ui.showError(e.getMessage());
            }
        }
    }

    /**
     * Starting point of the application.
     *
     * @param args Command-line arguments (not used).
     */
    public static void main(String[] args) throws InvalidStorageFilePathException, IOException {
        new Wing("./data/wing.txt").run();
    }

}
