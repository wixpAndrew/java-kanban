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
        subtasks.clear();
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
      subtasks.remove(subtaskID);
      int index = -1;
      Subtask subtask1 = subtasks.get(subtaskID);
      for (Epic epic1: epics.values()){ // проходимся по эпикам
          if (epic1.getId() == subtask1.getEpicId()){ // сравниваем эпик айди
              ArrayList<Subtask> ar = epic1.getAllSubTasks();
              for (Subtask subtask : ar){ // проходимся по сабтаскам в листе конкретного эпика
                  if (subtask.getId() == subtask1.getId()){ // сравниваем айдишники сабтасок
                      index = ar.indexOf(subtask);// сохраняем индекс в списке найденной сабтаски
                      break; // если успешно, то break тк айдишник сабтаски всегда уникальный
                  }
              }
          }
          if (index != -1){
              epic1.getAllSubTasks().remove(index);
              break;
          }
      }
    }

    @Override
    public void updateSubTask(Subtask subtask) {
        boolean res =false;
        for(Epic epic : epics.values()){
            if (epic.getId() == subtask.getEpicId()){
                for (Subtask subtask1 : epic.getAllSubTasks()){
                    if ( subtask1.getId() == subtask.getId()){
                        subtask1.setDescription(subtask.getDescription());
                        subtask1.setStatus(subtask.getStatus());
                        subtask1.setName(subtask.getName());
                        res = true;
                        break;
                    }
                }
            }
            if (res){
                epic.calculateStatus();
                break;
            }
        }
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