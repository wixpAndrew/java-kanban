package task;


import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Subtask extends Task { // подзадача
    private Integer epicId;
    public  LocalDateTime startTime;
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
    Duration duration;
    LocalDateTime endTime;

    public Subtask(String name, String description, Progress status) {
        super(name, status, description);
        if (status.equals(Progress.IN_PROGRESS)) {
            String str = LocalDateTime.now().format(formatter);
            startTime= LocalDateTime.parse(str, formatter);
        }
        else if (status.equals(Progress.DONE)) {
            endTime = LocalDateTime.now();
        }
    }

    public Integer getEpicId() {
        return epicId;
    }

    public LocalDateTime getStartTime() {
        if (startTime != null) {
            return startTime;
        }
        else {
            return null;
        }
    }

    public LocalDateTime getEndTime() {
        if (startTime != null && duration != null && endTime == null) {
            return startTime.plusMinutes(duration.toMinutes());
        } else if (endTime != null) {
            return endTime;
        }
        else {
            System.out.println("Ошибка при возвращении конеч. времени");
            return null;
        }
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