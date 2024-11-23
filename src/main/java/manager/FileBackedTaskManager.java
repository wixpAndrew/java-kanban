package manager;

import task.*;
import java.io.*;
import java.util.*;

public class FileBackedTaskManager extends InMemoryTaskManager {
    private final File file;

    public FileBackedTaskManager(File file) {
        this.file = file;
    }

    public void save() {
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
        save();
        return res;
    }

    @Override
    public int addEpic(Epic epic) {
        int res = super.addEpic(epic);
        save();
        return res;
    }

    @Override
    public int addTask(Task task) {
        int res = super.addTask(task);
        save();
        return res;
    }

    @Override
    public void deleteTask(int taskID) {
        super.deleteTask(taskID);
        save();
    }

    @Override
    public void updateTask(Task task) {
        super.addTask(task);
        save();
    }

    @Override
    public void deleteAllEpics() {
        for (Epic epic : super.getAllEpics()) {
            super.getHistoryManager().remove(epic.getId());
        }
        super.deleteAllEpics();
        super.deleteSubtasks();
        save();
    }

    @Override
    public void deleteEpic(int taskID) {
        for (Integer key : super.getSubTasksMap().keySet()) {
            if (super.getSubtaskById(key).getEpicId() == taskID) {
                super.deleteSub(key);
            }
        }
        super.deleteEpic(taskID);
        super.getHistoryManager().remove(taskID);
        save();
    }

    @Override
    public void updateEpic(Epic epic) {
        super.addEpic(epic);
        save();
    }

    @Override
    public void deleteSubtasks() {
        getAllSubs().clear();
        for (Epic epic : super.getAllEpics()) {
            for (Subtask subtask : epic.getAllSubTasks()) {
                super.deleteSub(subtask.getId());
            }
            epic.deleteAllSubTasks();
        }
        save();
    }

    @Override
    public void deleteSub(int subtaskID) {
        Subtask subtask1 = super.getSubTasksMap().get(subtaskID);
        Epic epic1 = super.getEpicById(subtask1.getEpicId());
        epic1.deleteSubTask(subtask1);
        super.deleteSub(subtaskID);
        epic1.calculateStatus();
        super.getHistoryManager().remove(subtaskID);
        save();
    }

    @Override
    public void updateSubTask(Subtask subtask) {
        super.addSubtask(subtask);
        Epic epic = super.getEpicById(subtask.getEpicId());
        epic.addSubTask(subtask);
        epic.calculateStatus();
        save();
    }
}
