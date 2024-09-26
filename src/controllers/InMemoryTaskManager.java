package controllers;

import java.util.*;
import java.util.stream.Collectors;

import model.*;

public class InMemoryTaskManager implements ITaskManager {
    private HashMap<Integer, Epic> epics = new HashMap<>();
    private HashMap<Integer, Task> tasks = new HashMap<>();
    private int count = 0;

    public int generateId() {
        return ++count;
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
        Managers.getDefaultHistory().add(tasks.get(id).copy());
        return tasks.get(id).copy();
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
        Task taskOnId = tasks.get(taskId);
        taskOnId.setName(task.getName());
        taskOnId.setDescription(task.getDescription());
        taskOnId.setStatus(task.getStatus());
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
        return epics.values();
    }

    @Override
    public Epic getEpicById(Integer id) {
        Managers.getDefaultHistory().add(epics.get(id).copy());
        return epics.get(id).copy();
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
        Epic epicOnId = epics.get(epicId);
        epicOnId.setName(epic.getName());
        epicOnId.setDescription(epic.getDescription());
        epicOnId.setStatus(epic.getStatus());
    }

    // Subtasks
    //----------------------------------------------------------------
    @Override
    public int addSubTaskToEpic(Subtask subtask, int epicId) {
        if (epics.containsKey(epicId)) {
            subtask.setId(generateId());
            epics.get(epicId).addSubTask(subtask);
        }
        return subtask.getId();
    }

    @Override
    public ArrayList<Subtask> printEpicSubTasks(Epic epic) {
        return epic.returnAllSubTasks();
    }

    @Override
    public void removeAllSubs() {
        for (Epic epic : epics.values()) {
            epic.removeAll();
        }
    }

    @Override
    public void deleteSub(Epic epic, int id) {
        epic.removeSubTask(id);
    }

    @Override
    public void updateSubTask(int epicId, int subTaskId, Subtask subtask) {
        Epic epic = this.epics.get(epicId);
        if (epic != null) {
            epic.updateSubTask(subtask, subTaskId);
        }
    }

    @Override
    public Subtask returnSubTaskById(Integer id) {
        for (Epic epic : epics.values()) {
            for (Subtask miniSub : printEpicSubTasks(epic)) {
                if (miniSub.getId() == id) {
                    Managers.getDefaultHistory().add(miniSub);
                    return miniSub.copy();
                }
            }
        }
        return null;
    }

}