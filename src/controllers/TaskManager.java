package controllers;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import model.*;

public class TaskManager {
    private HashMap<Integer, Epic> epics = new HashMap<>();
    private HashMap<Integer, Task> tasks = new HashMap<>();
    private int count = 0;
    private int generateId(){
        return  ++count;
    }

    // Task
    public int addTask(Task task){
        task.setId(generateId());
        tasks.put(task.getId(), task);
        return task.getId();
    }

    public ArrayList<Task> getTasks() {
        return new ArrayList<>(tasks.values());
    }

    public Task getTaskById(Integer id){
        return tasks.get(id).copy();
    }

    public void deleteAllTasks(){
        tasks.clear();
    }
    public void deleteTask(int taskID){
        tasks.remove(taskID);
    }

    public void updateTask(int taskId, Task task){
        Task taskOnId = tasks.get(taskId);
        taskOnId.setName(task.getName());
        taskOnId.setDescription(task.getDescription());
        taskOnId.setStatus(task.getStatus());
    }


    // Epic
    public int addEpic(Epic epic){
        epic.setId(generateId());
        epics.put(epic.getId(), epic);
        return epic.getId();
    }

    public Collection<Epic> getAllEpics(){
        return epics.values();
    }

    public Epic getEpicById(Integer id){
        return epics.get(id).copy();
    }

    public void deleteAllEpics(){
        epics.clear();
    }

    public void deleteEpic(int taskID){
        epics.remove(taskID);
    }

    public void updateEpic(int epicId, Epic epic){
        Epic epicOnId = epics.get(epicId);
        epicOnId.setName(epic.getName());
        epicOnId.setDescription(epic.getDescription());
        epicOnId.setStatus(epic.getStatus());
    }

    // Subtasks

    public int addSubTaskToEpic(Subtask subtask, int epicId){
        if (epics.containsKey(epicId)){
            subtask.setId(generateId());
            epics.get(epicId).addSubTask(subtask);
        }
        return subtask.getId();
    }

    public ArrayList<Subtask> printEpicSubTasks(Epic epic){
        return epic.returnAllSubTasks();
    }

    public void removeAllSubs(){
        for (Epic epic : epics.values()){
            epic.removeAll();
        }
    }
    public void deleteSub(Epic epic, int id){
        epic.removeSubTask(id);
    }
    public void updateSubTask(int epicId, int subTaskId, Subtask subtask){
        Epic epic = this.epics.get(epicId);
        if (epic != null){
            epic.updateSubTask(subtask, subTaskId);
        }
    }
}



