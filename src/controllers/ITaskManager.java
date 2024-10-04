package controllers;
import java.util.List;
import model.Epic;
import model.Subtask;
import model.Task;

import java.util.ArrayList;
import java.util.Collection;

public interface ITaskManager {

    // Task
    int addTask(Task task);

    ArrayList<Task> getTasks();

    Task getTaskById(Integer id);

    void deleteAllTasks();

    void deleteTask(int taskID);

    public void updateTask(int TaskId, Task task);

    // Epic
    int addEpic(Epic epic);

    Collection<Epic> getAllEpics();

    Epic getEpicById(Integer id);

    void deleteAllEpics();

    void deleteEpic(int taskID);

    void updateEpic(int epicId, Epic epic);

    int addSubTaskToEpic(Subtask subtask, int epicId);

    ArrayList<Subtask> getAllEpicSubTasks(int epicId);

    void removeAllSubs();

    void deleteSub( int id);

    void updateSubTask(int epicId, int subTaskId, Subtask subtask);

    public Subtask returnSubTaskById(Integer id);
    public List<Task> getHistory();
}