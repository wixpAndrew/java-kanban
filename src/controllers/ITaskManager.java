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

    public void updateTask(Task task);

    // Epic
    int addEpic(Epic epic);

    Collection<Epic> getAllEpics();

    Epic getEpicById(Integer id);

    void deleteAllEpics();

    void deleteEpic(int taskID);

    void updateEpic(Epic epic);

    int  createSubtask(Subtask subtask);

    ArrayList<Subtask> getAllEpicSubTasks(int epicId);

    void removeAllSubs();

    void deleteSub(int id);

    void updateSubTask(Subtask subtask);

     Subtask getSubtaskById(Integer id);
     List<Task> getHistory();
}