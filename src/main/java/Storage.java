import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;

public class Storage {

    public static final String DEFAULT_STORAGE_FILEPATH = "./data/wing.txt";

    public final Path path;

    public Storage(String filePath) throws InvalidStorageFilePathException {
        path = Paths.get(filePath);
        if (!isValidPath(path)) {
            throw new InvalidStorageFilePathException("Storage file should end with '.txt'");
        }
    }

    private static boolean isValidPath(Path filePath) {
        return filePath.toString().endsWith(".txt");
    }

    public void saveTaskList(TaskList taskList) throws StorageOperationException {
        try {
            FileWriter fw = new FileWriter(DEFAULT_STORAGE_FILEPATH);
            for (Task task : taskList.getTasks()) {
                fw.write(task.toString());
                fw.write(System.lineSeparator());
            }
            fw.close();
        } catch (IOException ioe) {
            throw new StorageOperationException("Error writing to file: " + path);
        }
    }

    public TaskList loadTaskList() throws StorageOperationException {

        if (!Files.exists(path) || !Files.isRegularFile(path)) {
            return new TaskList();
        }
        TaskList loadedTaskList = new TaskList();
        try {
            File file = new File(DEFAULT_STORAGE_FILEPATH);
            Scanner s = new Scanner(file);
            while (s.hasNextLine()) {
                Task loadedTask = decodedLineToTask(s.nextLine());
                loadedTaskList.add(loadedTask);
            }
            return loadedTaskList;
        } catch (FileNotFoundException fnfe) {
            throw new AssertionError("File not found: " + path);
        }
    }

    private static Task decodedLineToTask(String line) {
        char taskType = line.charAt(1);
        boolean isTaskDone = (line.charAt(4) == 'X');
        String taskDescription;
        switch (taskType) {
        case 'T':
            taskDescription = line.substring(7);
            return new Todo(taskDescription, isTaskDone);

        case 'D':
            int byIndex = line.indexOf('(');
            taskDescription = line.substring(7, byIndex - 1);
            String by = line.substring(byIndex + 5, line.length() - 1);
            return new Deadline(taskDescription, by, isTaskDone);

        case 'E':
            int fromIndex = line.indexOf('(');
            taskDescription = line.substring(7, fromIndex - 1);
            String startToEndDate = line.substring(fromIndex + 1, line.length() - 1);
            return new Event(taskDescription, startToEndDate, isTaskDone);

        default:
            throw new AssertionError("Invalid task type: " + taskType);
        }

    }

    //As mentioned in addressbook-Level2, Note the use of nested classes below.

    public static class InvalidStorageFilePathException extends Exception {
        public InvalidStorageFilePathException(String message) {
            super(message);
        }
    }

    public static class StorageOperationException extends Exception {
        public StorageOperationException(String message) {
            super(message);
        }
    }

}
