package task;

import javax.print.attribute.SupportedValuesAttribute;

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

    public String SubTasktoString() {
        return this.getId() + "," + "SUBTASK" + "," + this.getName() + "," + this.getStatus() + "," + this.getDescription() + "," + this.getEpicId() + ",";

    }
}