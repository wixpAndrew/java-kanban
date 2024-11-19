package manager;

import org.junit.jupiter.api.Test;
import task.*;

import java.io.*;
import java.util.*;

public class FileBackedTaskManager extends InMemoryTaskManager {
    private final File file;

    public FileBackedTaskManager(File file) {
        this.file = file;
    }

    public void save() throws  ManagerSaveException {
        try (Writer fileWriter = new FileWriter(file)) {
            fileWriter.write("id,type,name,status,description,epic\n");
            for (Task task : super.getTasks()) {
                fileWriter.write(task.tasktoString());
                fileWriter.write("\n");
            }
            for (Epic epic : super.getAllEpics()) {
                fileWriter.write(epic.epictoString());
                fileWriter.write("\n");
            }
            for (Subtask subtask : super.getAllSubs()) {
                fileWriter.write(subtask.subTasktoString());
                fileWriter.write("\n");
            }
        } catch (IOException exception) {
            throw new ManagerSaveException("Ошибка сохранения!");
        }
    }

    public static FileBackedTaskManager loadFromFile(File file) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(file));
        FileBackedTaskManager fileBackedTaskManager = new FileBackedTaskManager(file);
        String line;
        reader.readLine();
        while ((line = reader.readLine()) != null) {
            List<String> splitLine = new ArrayList<>(Arrays.asList(line.split(",")));

            switch (splitLine.get(1)) {
                case "TASK" -> {
                    Task task = new Task(splitLine.get(2), Progress.valueOf(splitLine.get(3)), splitLine.get(4));
                    task.setId(Integer.parseInt(splitLine.get(0)));
                    fileBackedTaskManager.addTask(task);
                }
                case "EPIC" -> {
                    Epic epic = new Epic(splitLine.get(2), splitLine.get(4));
                    epic.setStatus(Progress.valueOf(splitLine.get(3)));
                    epic.setId(Integer.parseInt(splitLine.get(0)));
                    fileBackedTaskManager.addEpic(epic);
                }
                case "SUBTASK" -> {
                    Subtask subtask = new Subtask(splitLine.get(2), splitLine.get(4), Progress.valueOf(splitLine.get(3)));
                    subtask.setId(Integer.parseInt(splitLine.get(0)));
                    subtask.setEpicId(Integer.parseInt(splitLine.get(5)));
                    fileBackedTaskManager.addSubtask(subtask);
                }
            }
        }
        reader.close();
        return fileBackedTaskManager;
    }

    @Override
    public int addSubtask(Subtask subtask) {
        int res = super.addSubtask(subtask);
        try{
            save();
        } catch (ManagerSaveException exception) {
            System.out.println(exception.getMessage());
        }
        return res;
    }

    @Override
    public int addEpic (Epic epic) {
        int res = super.addEpic(epic);
        try{
            save();
        } catch (ManagerSaveException exception) {
            System.out.println(exception.getMessage());
        }
        return res;
    }

    @Override
    public int addTask(Task task) {
        int res = super.addTask(task);
        try{
            save();
        } catch (ManagerSaveException exception) {
            System.out.println(exception.getMessage());
        }
        return res;
    }

}
