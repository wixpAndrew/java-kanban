package model;

public class Subtask extends Task { // подзадача
    private Integer epicId;
    public Subtask(String name, String description, Progress status){
        super(name, status, description);
    }
    public void setEpicId(Integer epicId){
        this.epicId = epicId ;
    }
    public Integer getEpicId(){
        return epicId;
    }

    public Subtask copy() {
        Subtask result = (Subtask) super.copy();
        result.setEpicId(this.epicId);
        return result;
    }
}