package task;


import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Subtask extends Task { // подзадача
    private Integer epicId;

    public Subtask(String name, String description, Progress status) {
        super(name, status, description);
        if (status.equals(Progress.IN_PROGRESS)) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSSSS");
            String str = LocalDateTime.now().format(formatter);
            startTime = LocalDateTime.parse(str, formatter);
        } else if (status.equals(Progress.DONE)) {
            endTime = LocalDateTime.now();
        }
    }

    public Integer getEpicId() {
        return epicId;
    }


    public void setEpicId(Integer epicId) {
        this.epicId = epicId;
    }

    public String subTasktoString() {
        return this.getId() + "," + "SUBTASK" + "," +
                this.getName() + "," +
                this.getStatus() + "," +
                this.getDescription() + "," +
                this.getEpicId() + "," +
                this.getStartTime() + "," +
                this.getDuration();
    }

    public void setStartTime(LocalDateTime start) {
        this.startTime = start;
    }

    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }

    public void setDuration(Duration duration) {
        this.duration = duration;
    }

    public Duration getDuration() {
        return duration;
    }
}