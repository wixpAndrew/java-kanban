import java.util.HashMap;

public class Subtask extends Task { // подзадача
    Integer epicId;
    public Subtask(String name, String description, Progress status, int id){
        super(name, status,  id, description);
    }
    public void setEpicId(Integer epicId){
        this.epicId = epicId ;
    }
    public Integer getEpicId(){
        return epicId;
    }
}
