package manager;

import task.Epic;
import task.Managers;
import task.Subtask;
import task.Task;

import java.util.*;

public class InMemoryTaskManager implements ITaskManager {
    private final HistoryManager historyManager;
    private final Map<Integer, Epic> epics = new HashMap<>();
    private final Map<Integer, Task> tasks = new HashMap<>();
    private final Map<Integer, Subtask> subtasks = new HashMap<>();
    private int count = 0;
    Set<Task> prioritTasks = new TreeSet<>(Comparator.comparing(Task::getStartTime));

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
        return new ArrayList<>(tasks.values());
    }

    @Override
    public Task getTaskById(Integer id) {
        historyManager.add(tasks.get(id));
        return tasks.get(id);
    }

    @Override
    public void deleteAllTasks() {
        tasks.values().forEach((n) -> historyManager.remove(n.getId()));
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

    @Override
    public Map<Integer, Task> getTaskMap() {
        return tasks;
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
        epics.values().forEach((n) -> historyManager.remove(n.getId()));
        epics.clear();
        subtasks.clear();
    }

    @Override
    public void deleteEpic(int taskID) {
        subtasks.keySet().stream()
                .filter((n) -> subtasks.get(n).getEpicId() == taskID)
                .forEach(subtasks::remove);

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
        if (subtask.getId() == null) {
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
    public void deleteSubtasks() {
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

    @Override
    public Map<Integer, Subtask> getSubTasksMap() {
        return subtasks;
    }

    // ----------------------------------------------------------------------------------------------

    @Override
    public List<Task> getHistory() {
        return historyManager.getHistory();
    }

    @Override
    public HistoryManager getHistoryManager() {
        return historyManager;
    }

    @Override
    public List<Task> getPrioritizedTasks() {
        return new ArrayList<Task>(prioritTasks);
    }

    @Override
    public boolean isItRightWorkingTasks() {
        boolean res = false;
        List<Task> lis = new ArrayList<>(prioritTasks);

        for (int i = 0; i < lis.size() - 1; i++) {
            Task task1 = lis.get(i);
            Task task2 = lis.get(i + 1);

            // Проверка на null для времени начала и окончания
            if (task1.getStartTime() == null || task1.getEndTime() == null || task2.getStartTime() == null || task2.getEndTime() == null) {
                throw new IllegalArgumentException("Ошибка при выводе времени.");
            }

            if (task2.getStartTime().isBefore(task1.getEndTime())) {
                res = true;
                break;
            }
        }
        return res;
}
    }