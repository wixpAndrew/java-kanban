import javax.sound.sampled.Line;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Objects;

public class TaskManager {
    public HashMap<Integer, Epic> epics = new HashMap<>();
    private HashMap<Integer, Task> tasks = new HashMap<>();
    int count = 0;
    private int generateId(){
        return  ++count;
    }

   public void add(Epic epic){
        epic.setId(generateId());
        epics.put(epic.getId(), epic);
   }
   public void addSubTaskToEpic(Subtask subtask, int epicId){
        if (epics.containsKey(epicId)){
            epics.get(epicId).addSubTask(subtask);
        }
   }

    public Collection<Task> allTasks(){
        return  tasks.values();
    }
    public void deleteAllTasks(){
        tasks.clear();
    }
    public void deleteTask(int taskID){
        tasks.remove(taskID);
    }
    public void addTask(Task task){
        task.setId(generateId());
        tasks.put(task.getId(), task);
    }
    public void updateTask(int taskId, Task task){
        Task taskOnId = tasks.get(taskId);
        taskOnId.setName(task.getName());
        taskOnId.setDescription(task.getDescription());
        taskOnId.setStatus(task.getStatus());
    }
    public Task getTaskById(Integer id){
        return tasks.get(id);
    }

    public Collection<Epic> allEpics(){
        return epics.values();
    }
    public void deleteAllEpics(){
        epics.clear();
    }
    public void deleteEpic(int taskID){
        epics.remove(taskID);
    }
    public void addEpic(Epic epic){
        epic.setId(generateId());
        tasks.put(epic.getId(), epic);
    }
    public void updateEpic(int taskId, Epic epic){
        Epic epicOnId = epics.get(taskId);
        epicOnId.setName(epic.getName());
        epicOnId.setDescription(epic.getDescription());
        epicOnId.setStatus(epic.getStatus());
    }
    public Epic getEpicById(Integer id){
        return epics.get(id);
    }
    public ArrayList<Subtask> printEpicSubTasks(Epic epic){
        return epic.returnAllSubTasks();
    }
    public void makeEpicStatus(Epic epic) {
        boolean res = false;
        for (Subtask subtask : epic.subList){
            if ( subtask.getStatus() == Progress.NEW){
                res = true;
            }
            else {
                res = false;
                break;
            }
        }
            if (epic.subList == null || res) {
                epic.setStatus(Progress.NEW);
                return;
            }
            else {
                for (Subtask sub : epic.subList) {
                    if (sub.getStatus() == Progress.DONE) {
                        epic.setStatus(Progress.DONE);
                    }
                    else {
                        epic.setStatus(Progress.IN_PROGRESS);
                        break;
                    }
                }
            }
        }
    }

