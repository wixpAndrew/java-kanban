package manager;

import task.Epic;
import task.Managers;
import task.Subtask;
import task.Task;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class InMemoryTaskManager implements ITaskManager {
    private final HistoryManager historyManager;
    private final Map<Integer, Epic> epics = new HashMap<>();
    private final Map<Integer, Task> tasks = new HashMap<>();
    private final Map<Integer, Subtask> subtasks = new HashMap<>();
    private int count = 0;

    public InMemoryTaskManager() {
        historyManager = Managers.getDefaultHistory();
    }

    private int generateId() {
        return ++count;
    }
    // Task
    //-----------------------------------------------------------

    @Override
    public int addTask(Task task) {
        if (task.getId() == null) {
            task.setId(generateId());
        }
        tasks.put(task.getId(), task);
        return task.getId();
    }

    @Override
    public List<Task> getTasks() {
        return new  ArrayList<>(tasks.values());
    }

    @Override
    public Task getTaskById(Integer id) {
        historyManager.add(tasks.get(id));
        return tasks.get(id);
    }

    @Override
    public void deleteAllTasks() {
        for (Task task : tasks.values()) {
            historyManager.remove(task.getId());
        }
        tasks.clear();
    }

    @Override
    public void deleteTask(int taskID) {
        tasks.remove(taskID);
        historyManager.remove(taskID);
    }

    @Override
    public void updateTask(Task task) {
        tasks.put(task.getId(), task);
    }

    // Epic
    // -------------------------------------------------------------------------------------------------
    @Override
    public int addEpic(Epic epic) {
        if (epic.getId() == null) {
            epic.setId(generateId());
        }
        epics.put(epic.getId(), epic);
        return epic.getId();
    }

    @Override
    public List<Epic> getAllEpics() {
        return new ArrayList<>(epics.values());
    }

    @Override
    public Epic getEpicById(Integer id) {
        historyManager.add(epics.get(id));
        return epics.get(id);
    }

    @Override
    public void deleteAllEpics() {
        for (Epic epic : epics.values()) {
            historyManager.remove(epic.getId());
        }
        epics.clear();
        subtasks.clear();
    }

    @Override
    public void deleteEpic(int taskID) {
        for (Integer key : subtasks.keySet()) {
            if (subtasks.get(key).getEpicId() == taskID) {
                subtasks.remove(key);
            }
        }
        epics.remove(taskID);
        historyManager.remove(taskID);
    }

    @Override
    public void updateEpic(Epic epic) {
        epics.put(epic.getId(), epic);
    }

    // Subtasks
    //---------------------------------------------------------------------------------------------
    @Override
    public int addSubtask(Subtask subtask) {
        if (subtask.getId() == null ) {
            subtask.setId(generateId());
        }
        subtasks.put(subtask.getId(), subtask);
        epics.get(subtask.getEpicId()).addSubTask(subtask);
        return subtask.getId();
    }

    @Override
    public List<Subtask> getAllEpicSubTasks(int epicID) {
        return epics.get(epicID).getAllSubTasks();
    }

    @Override
    public void removeAllSubs() {
        subtasks.clear();
        for (Epic epic : epics.values()) {
            for (Subtask subtask : epic.getAllSubTasks()) {
                historyManager.remove(subtask.getId());
            }
            epic.deleteAllSubTasks();
        }
    }

    @Override
    public void deleteSub(int subtaskID) {
        Subtask subtask1 = subtasks.get(subtaskID);
        Epic epic1 = epics.get(subtask1.getEpicId());
        epic1.deleteSubTask(subtask1);
        subtasks.remove(subtaskID);
        epic1.calculateStatus();
        historyManager.remove(subtaskID);
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
    public List<Subtask> getAllSubs() {
        return new ArrayList<>(subtasks.values());
    }

    // ----------------------------------------------------------------------------------------------
    @Override
    public List<Task> getHistory() {
        return historyManager.getHistory();
    }
}