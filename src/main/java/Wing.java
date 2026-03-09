import java.io.IOException;

public class Wing {

    private static Storage storage;
    private static TaskList tasks;
    private static Ui ui;

    public Wing(String filePath) throws Storage.InvalidStorageFilePathException {
        ui = new Ui();
        storage = new Storage(filePath);
        try {
            tasks = new TaskList(storage.loadTaskList());
        } catch (IOException e) {
            ui.showError("File not found! Don't worry, I'll create one!");
            tasks = new TaskList();
        }
    }

    public void run() throws IOException {
        ui.showWelcome();
        ui.showFileContents();
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

    public static void main(String[] args) throws Storage.InvalidStorageFilePathException, IOException {
        new Wing("./data/wing.txt").run();
    }

}
