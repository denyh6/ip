package wing.storage;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

import wing.task.Deadline;
import wing.task.Event;
import wing.task.Task;
import wing.task.Todo;
import wing.tasklist.TaskList;

public class Storage {

    public static final String DEFAULT_STORAGE_FOLDERPATH = "./data";
    public static final String DEFAULT_STORAGE_FILEPATH = "./data/wing.txt";
    public final String filePath;

    public Storage(String filePath) throws InvalidStorageFilePathException {
        if (!isValidPath(filePath)) {
            throw new InvalidStorageFilePathException("wing.storage.Storage file should end with '.txt'");
        }

        this.filePath = DEFAULT_STORAGE_FILEPATH;
    }

    private static boolean isValidPath(String filePath) {
        return filePath.endsWith(".txt");
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
            throw new StorageOperationException("Error writing to file: " + filePath);
        }
    }

    public ArrayList<Task> loadTaskList() throws IOException {
        File dir = new File(DEFAULT_STORAGE_FOLDERPATH);
        if (!dir.exists()) {
            boolean isFolderCreated = dir.mkdir();
            if (!isFolderCreated) {
                throw new IOException("Unable to create folder: " + DEFAULT_STORAGE_FOLDERPATH);
            }
            throw new IOException("File not found!");
        }
        File file = new File(DEFAULT_STORAGE_FILEPATH);
        if (!file.exists()) {
            boolean isFileCreated = file.createNewFile();
            if (!isFileCreated) {
                throw new IOException("Unable to create file: " + DEFAULT_STORAGE_FILEPATH);
            }
            throw new IOException("File not found!");
        }
        ArrayList<Task> loadedTaskList = new ArrayList<>();
        Scanner s = new Scanner(file);
        while (s.hasNextLine()) {
            Task loadedTask = decodedLineToTask(s.nextLine());
            loadedTaskList.add(loadedTask);
        }
        return loadedTaskList;

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
