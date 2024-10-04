package controllers;

import java.beans.DefaultPersistenceDelegate;
import java.util.*;
import java.util.stream.Collectors;

import model.*;

public class InMemoryTaskManager implements ITaskManager {
    private  HistoryManager historyManager = null;
    private HashMap<Integer, Epic> epics = new HashMap<>();
    private HashMap<Integer, Task> tasks = new HashMap<>();
    private HashMap<Integer,Subtask> subtasks = new HashMap<>();
    private int count = 0;

    private int generateId() {
        return ++count;
    }

    public InMemoryTaskManager(){
        historyManager = Managers.getDefaultHistory();
    }
    // Task
    //-----------------------------------------------------------

    @Override
    public int addTask(Task task) {
        task.setId(generateId());
        tasks.put(task.getId(), task);
        return task.getId();
    }

    @Override
    public ArrayList<Task> getTasks() {
        return new ArrayList<>(tasks.values());
    }

    @Override
    public Task getTaskById(Integer id) {
        historyManager.add(tasks.get(id));
        return tasks.get(id);
    }

    @Override
    public void deleteAllTasks() {
        tasks.clear();
    }

    @Override
    public void deleteTask(int taskID) {
        tasks.remove(taskID);
    }

    @Override
    public void updateTask(int taskId, Task task) {
        Task taskOnId = tasks.get(task.getId());
        tasks.put(taskId, task);
    }

    // Epic
    // -----------------------------------------------------------
    @Override
    public int addEpic(Epic epic) {
        epic.setId(generateId());
        epics.put(epic.getId(), epic);
        return epic.getId();
    }

    @Override
    public Collection<Epic> getAllEpics() {
        return new ArrayList<>(epics.values());
    }

    @Override
    public Epic getEpicById(Integer id) {
        historyManager.add(epics.get(id));
        return epics.get(id);
    }

    @Override
    public void deleteAllEpics() {
        epics.clear();
    }

    @Override
    public void deleteEpic(int taskID) {
        epics.remove(taskID);
    }

    @Override
    public void updateEpic(int epicId, Epic epic) {
        Epic epicOnId = epics.get(epic.getId());
        epicOnId.setName(epic.getName());
        epicOnId.setDescription(epic.getDescription());
    }

    // Subtasks
    //----------------------------------------------------------------
    @Override
    public int addSubTaskToEpic(Subtask subtask, int epicId) {
        subtask.setId(generateId());
        subtask.setEpicId(epicId);
        subtasks.put(subtask.getId(), subtask);
        return subtask.getId();
    }

    @Override
    public ArrayList<Subtask> getAllEpicSubTasks(int epicID) {
        ArrayList<Subtask> ar = new ArrayList<>();
        for (Subtask subtask : subtasks.values()){
            if (subtask.getEpicId() == epics.get(epicID).getId()){
                ar.add(subtask);
            }
        }
        return ar;
    }

    @Override
    public void removeAllSubs() {
        subtasks.clear();
    }

    @Override
    public void deleteSub(int subtaskID) {
      subtasks.remove(subtaskID);
    }

    @Override
    public void updateSubTask(int epicId, int subTaskId, Subtask subtask) {
        subtask.setId(subTaskId);
        subtask.setEpicId(epicId);
        subtasks.put(subTaskId, subtask);
    }
    @Override
    public Subtask returnSubTaskById(Integer id) {
        return subtasks.get(id);
    }
    @Override
    public List<Task> getHistory(){
        return historyManager.getHistory();
    }
}