package task;


import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Subtask extends Task { // подзадача
    private Integer epicId;
    public static LocalDateTime startTime;
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
        return startTime = LocalDateTime.parse(LocalDateTime.now().format(formatter), formatter);
    }

    public LocalDateTime getEndTime () {
        return endTime = LocalDateTime.parse(LocalDateTime.now().format(formatter), formatter);
    }

    public void setEpicId(Integer epicId) {
        this.epicId = epicId;
    }

    public String subTasktoString() {
        return this.getId() + "," + "SUBTASK" + "," + this.getName() + "," + this.getStatus() + "," + this.getDescription() + "," + this.getEpicId() + ",";
    }
}