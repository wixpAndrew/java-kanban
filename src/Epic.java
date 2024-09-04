import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

public class Epic extends Task{
    ArrayList<Subtask> subList = new ArrayList<>();

    public Epic(String name, String description, int id, Progress status){
        super(name,status, id, description);
    }

    public void addSubTask(Subtask subtask){
        subList.add(subtask);
    }
    public void removeTask(int id){
        int index = -1;
        for(Subtask sub : subList){
            if (sub.getId() == id){
                index = subList.indexOf(sub);
                break;
            }
        }
        if (index >= 0){
            subList.remove(index);
        }
    }
    public void removeAll(){
        subList.clear();
    }
    public ArrayList<Subtask> returnAllSubTasks(){
        return subList;
    }
    public Subtask returnSubTaskById(Integer id){
        for (Subtask miniSub : subList){
            if (miniSub.getId() == id){
                return miniSub;
            }
        }
        return null;
    }
    public void updateSubTask(Subtask subtask2, int idSub){
        int index = -1;
        for (Subtask subtask : subList){
            if (subtask.getId() == idSub){
                index = subList.indexOf(subtask);
                break;
            }
        }
        Subtask sub =  subList.get(index);
        sub.setName(subtask2.getName());
        sub.setDescription(subtask2.getDescription());
        sub.setStatus(subtask2.getStatus());
    }
}



