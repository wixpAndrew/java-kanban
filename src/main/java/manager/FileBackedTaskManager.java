package manager;

import task.*;

import java.io.*;
import java.util.*;

public class FileBackedTaskManager extends InMemoryTaskManager {
    File file;
    ITaskManager inMemoryTaskManager = Managers.getDefault();

    public FileBackedTaskManager(File file) throws IOException {
        this.file = file;
    }

    public void save() throws IOException, ManagerSaveException {
        new FileWriter(file, false).close();
        Writer fileWriter = new FileWriter(file);
        fileWriter.write("id,type,name,status,description,epic\n");
        try {
            for (Task task : super.getTasks()) {
                fileWriter.write( task.TasktoString());
                fileWriter.write("\n");
            }
            for (Epic epic : super.getAllEpics()) {
                fileWriter.write( epic.EpictoString());
                fileWriter.write("\n");
            }
            for (Subtask subtask : super.getAllSubs()) {
                fileWriter.write( subtask.SubTasktoString());
                fileWriter.write("\n");
            }
        } catch (IOException exception) {
            throw  new ManagerSaveException("Ошибка сохранения!");
        }
        fileWriter.close();
    }

    public static FileBackedTaskManager loadFromFile(File file) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(file));
        FileBackedTaskManager fileBackedTaskManager = new FileBackedTaskManager(file);
        String line;
        reader.readLine();
        while ((line = reader.readLine()) != null) {
            ArrayList<String> arrayList = new ArrayList<>(Arrays.asList(line.split(",")));

            switch (arrayList.get(1)) {
                case "TASK" -> {
                    Task task = new Task(arrayList.get(2), Progress.valueOf(arrayList.get(3)),arrayList.get(4));
                    task.setId(Integer.parseInt(arrayList.get(0)));
                    fileBackedTaskManager.addTask(task);
                }
                case "EPIC" -> {
                    Epic epic = new Epic(arrayList.get(2), Progress.valueOf(arrayList.get(3)) ,arrayList.get(4));
                    epic.setId(Integer.parseInt(arrayList.get(0)));
                    fileBackedTaskManager.addEpic(epic);
                }
                case "SUBTASK" -> {
                    Subtask subtask = new Subtask(arrayList.get(2), arrayList.get(4), Progress.valueOf(arrayList.get(3)));
                    subtask.setId(Integer.parseInt(arrayList.get(0)));
                    subtask.setEpicId(Integer.parseInt(arrayList.get(5)));
                    fileBackedTaskManager.createSubtask(subtask);
                }
            }
        }
        reader.close();
        return fileBackedTaskManager;
    }
}
