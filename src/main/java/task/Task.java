package task;

public class Task {

    private String name;
    private Progress status;
    private Integer id;
    private String description;

    public Task(String name, Progress status, String description) {
        this.name = name;
        this.status = status;
        this.description = description;
        this.id = -1;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Progress getStatus() {
        return status;
    }

    public void setStatus(Progress status) {
        this.status = status;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String TasktoString(){
            return this.getId() + "," + "TASK" + "," + this.getName() + "," + this.getStatus() + "," + this.getDescription() + ",";

    }
}