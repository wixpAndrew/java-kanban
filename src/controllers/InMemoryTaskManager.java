package controllers;

import java.beans.DefaultPersistenceDelegate;
import java.util.*;
import java.util.stream.Collectors;

import model.*;

public class InMemoryTaskManager implements ITaskManager {
    private  HistoryManager historyManager = null;
    private HashMap<Integer, Epic> epics = new HashMap<>();
    private HashMap<Integer, Task> tasks = new HashMap<>();
    private HashMap<Integer, ArrayList<Subtask>> subtasks = new HashMap<>();
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
        if (subtasks.containsKey(epicId)) {
            subtasks.get(epicId).add(subtask);
        }
        else {
            ArrayList<Subtask> ar = new ArrayList<>();
            ar.add(subtask);
            subtasks.put(epicId, ar);
        }
         subtask.setId(generateId());
        return subtask.getId();
    }

    @Override
    public ArrayList<Subtask> getAllEpicSubTasks(Epic epic) {
        return subtasks.get(epic.getId());
    }

    @Override
    public void removeAllSubs() {
        subtasks.clear();
    }

    @Override
    public void deleteSub(Epic epic, int id) {
        ArrayList<Subtask> ar = subtasks.get(epic.getId());
        boolean res = false;
        int index = 0;
        for (Subtask subtask : ar){
            if (subtask.getId() == id){
                index = ar.indexOf(subtask);
                res = true;
                break;
            }
        }
        if (res){
            ar.remove(index);
        }
    }

    @Override
    public void updateSubTask(int epicId, int subTaskId, Subtask subtask) {
        Subtask subtask1 =  new Subtask(subtask.getName(), subtask.getDescription(), subtask.getStatus());
        ArrayList<Subtask> ar = subtasks.get(epicId);
        int index = 0;
        for (Subtask subtask2 : ar){
            if (subtask2.getId() == subTaskId){
                index = subtasks.get(epicId).indexOf(subtask2);
                break;
            }
        }
        if (index != 0){
            subtasks.get(epicId).set(index, subtask);
        }
    }
    @Override
    public Subtask returnSubTaskById(Integer id) {
        for (ArrayList<Subtask> ar : subtasks.values()) {
            for (Subtask subtask : ar) {
                if (subtask.getId() == id) {
                    return subtask;
                }
            }
        }
        return null;
    }
    @Override
    public ArrayList<Subtask> returnAllSubsOnEpicId(int epicId){
       return subtasks.get(epicId);
    }
    @Override
    public List<Task> getHistory(){
        return historyManager.getHistory();
    }
}