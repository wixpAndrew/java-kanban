import java.util.HashMap;

public class Task {// отдельная задача
    private String name;
    private Progress status;
    private Integer id;
    private String description;

    public Task(String name, Progress status, int id, String description){
        this.name = name;
        this.status = status;
        this.id = id;
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public int getId() {
        return id;
    }

    public Progress getStatus() {
        return status;
    }

    public String getDescription() {
        return description;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setStatus(Progress status) {
        this.status = status;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}