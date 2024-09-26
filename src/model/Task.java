package model;

public class Task {// отдельная задача
    private String name;
    private Progress status;
    private Integer id;
    private String description;

    public Task(String name,Progress status, String description){
        this.name = name;
        this.status = status;
        this.description = description;
    }
    public Task(String name, String description){
        this.name = name;
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

    public Task copy() {
        return new Task(this.name, this.status, this.description);
    }
}