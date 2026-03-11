package wing.storage;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

import wing.exception.InvalidStorageFilePathException;
import wing.exception.StorageOperationException;
import wing.task.Deadline;
import wing.task.Event;
import wing.task.Task;
import wing.task.Todo;
import wing.tasklist.TaskList;

/**
 * Represents the file used to store address book data.
 */
public class Storage {

    /**
     * Default file path used if the user doesn't provide the file name.
     */
    public static final String DEFAULT_STORAGE_FOLDERPATH = "./data";
    public static final String DEFAULT_STORAGE_FILEPATH = "./data/wing.txt";
    public final String filePath;

    /**
     * Constructs a Storage instance of the specified file.
     *
     * @param filePath File path of the wing.txt file.
     * @throws InvalidStorageFilePathException if the given file path is invalid
     */
    public Storage(String filePath) throws InvalidStorageFilePathException {
        if (!isValidPath(filePath)) {
            throw new InvalidStorageFilePathException("wing.storage.Storage file should end with '.txt'");
        }

        this.filePath = DEFAULT_STORAGE_FILEPATH;
    }

    /**
     * Returns true if the given path is acceptable as a storage file.
     * The file path is considered acceptable if it ends with '.txt'
     *
     * @param filePath File path of wing.txt.
     * @return true, if filePath ends with ".txt".
     */
    private static boolean isValidPath(String filePath) {
        return filePath.endsWith(".txt");
    }

    /**
     * Saves the {@code tasks} data to the storage file.
     *
     * @param taskList current TaskList to be saved to wing.txt.
     * @throws StorageOperationException if there are errors converting and/or storing data to file.
     */
    public void saveTaskList(TaskList taskList) throws StorageOperationException {
        try {
            FileWriter fw = new FileWriter(DEFAULT_STORAGE_FILEPATH);
            for (Task task : taskList.getTasks()) {
                fw.write(task.toString());
                fw.write(System.lineSeparator());
            }
            fw.close();
        } catch (IOException ioe) {
            throw new StorageOperationException("Error writing to file: " + filePath);
        }
    }

    /**
     * Loads the {@code Storage} data from this storage file, and then returns it as an ArrayList of Tasks.
     * Returns an empty {@code ArrayList} if the file or directory folder does not exist.
     *
     * @return ArrayList of Tasks extracted from wing.txt.
     * @throws IOException if there were errors reading and/or converting data from file, or creating it.
     */
    public ArrayList<Task> loadTaskList() throws IOException {
        File dir = new File(DEFAULT_STORAGE_FOLDERPATH);
        if (!dir.exists()) {
            boolean isFolderCreated = dir.mkdir();
            if (!isFolderCreated) {
                throw new IOException("Unable to create folder: " + DEFAULT_STORAGE_FOLDERPATH);
            }
        }

        File file = new File(DEFAULT_STORAGE_FILEPATH);
        if (!file.exists()) {
            boolean isFileCreated = file.createNewFile();
            if (!isFileCreated) {
                throw new IOException("Unable to create file: " + DEFAULT_STORAGE_FILEPATH);
            }
            return new ArrayList<>();
        }

        ArrayList<Task> loadedTaskList = new ArrayList<>();
        Scanner s = new Scanner(file);
        while (s.hasNextLine()) {
            Task loadedTask = decodedLineToTask(s.nextLine());
            loadedTaskList.add(loadedTask);
        }
        return loadedTaskList;

    }

    /**
     * Understands the toString representation of the tasks from the wing.txt file.
     * Generates a specific task (Todo, Deadline or Event) from each line of the file.
     *
     * @param line Each line in wing.txt that represents a saved Task.
     * @return Corresponding Task from a given line of the String representation of a task.
     */
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

}
