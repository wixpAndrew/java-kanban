package manager;

import task.Epic;
import task.Subtask;
import task.Task;

import java.util.Collection;
import java.util.List;
import java.util.Map;

public interface ITaskManager {

    // Task
    int addTask(Task task);

    List<Task> getTasks();

    Task getTaskById(Integer id);

    void deleteAllTasks();

    void deleteTask(int taskID);

    void updateTask(Task task);

    Map<Integer, Task> getTaskMap();

    // Epic
    int addEpic(Epic epic);

    Collection<Epic> getAllEpics();

    Epic getEpicById(Integer id);

    void deleteAllEpics();

    void deleteEpic(int taskID);

    void updateEpic(Epic epic);

    int addSubtask(Subtask subtask);

    List<Subtask> getAllEpicSubTasks(int epicId);

    void deleteSubtasks();

    void deleteSub(int id);

    void updateSubTask(Subtask subtask);

    Subtask getSubtaskById(Integer id);

    List<Subtask> getAllSubs();

    List<Task> getHistory();

    HistoryManager getHistoryManager();

    Map<Integer, Subtask> getSubTasksMap();
}