package task;

public class Subtask extends Task { // подзадача
    private Integer epicId;

    public Subtask(String name, String description, Progress status) {
        super(name, status, description);
    }

    public Integer getEpicId() {
        return epicId;
    }

    public void setEpicId(Integer epicId) {
        this.epicId = epicId;
    }
}