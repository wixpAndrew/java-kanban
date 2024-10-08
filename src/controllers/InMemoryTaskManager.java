package controllers;

import java.util.*;

import model.*;
import org.junit.jupiter.api.extension.ExtendWith;

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
    public void updateTask(Task task) {
        tasks.put(task.getId(), task);
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
        subtasks.clear();
    }

    @Override
    public void deleteEpic(int taskID) {
        for (Integer key : subtasks.keySet()){
            if (subtasks.get(key).getEpicId() == taskID){
                subtasks.remove(key);
            }
        }
        epics.remove(taskID);
    }

    @Override
    public void updateEpic(Epic epic) {
        epics.put(epic.getId(), epic);
    }

    // Subtasks
    //----------------------------------------------------------------
    @Override
    public int createSubtask(Subtask subtask){
        subtask.setId(generateId());
        subtasks.put(subtask.getId(), subtask);
        epics.get(subtask.getEpicId()).addSubTask(subtask);
        return subtask.getId();
    }

    @Override
    public ArrayList<Subtask> getAllEpicSubTasks(int epicID) {
        return epics.get(epicID).getAllSubTasks();
    }

    @Override
    public void removeAllSubs() {
        subtasks.clear();
        for (Epic epic : epics.values()){
            epic.getAllSubTasks().clear();
        }
    }

    @Override
    public void deleteSub(int subtaskID) {
        Subtask subtask1 = subtasks.get(subtaskID);
        Epic epic1 = epics.get(subtask1.getEpicId());
        epic1.deleteSubTask(subtask1);
        subtasks.remove(subtaskID);
        epic1.calculateStatus();
    }

    @Override
    public void updateSubTask(Subtask subtask) {
        subtasks.put(subtask.getId(), subtask);
        Epic epic = epics.get(subtask.getEpicId());
        epic.addSubTask(subtask);
        epic.calculateStatus();
    }
    @Override
    public Subtask getSubtaskById(Integer id) {
        historyManager.add(subtasks.get(id));
        return subtasks.get(id);
    }
    @Override
    public List<Task> getHistory(){
        return historyManager.getHistory();
    }
}