package task;
import com.google.gson.annotations.Expose;
import server.User;

import java.io.Serializable;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Task implements Serializable {

    @Expose private String name;
    @Expose private Progress status;
    @Expose private Integer id;
    @Expose private String description;
    @Expose private LocalDateTime startTime;
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
    @Expose private Duration duration;
    @Expose private LocalDateTime endTime;


    public Task(String name, Progress status, String description) {
        this.name = name;
        this.status = status;
        this.description = description;

        if (status == null) {
            this.status = null;
        } else if (status.equals(Progress.IN_PROGRESS)) {
            String strTime = LocalDateTime.now().format(formatter);
            startTime = LocalDateTime.parse(strTime, formatter);
        } else if (status.equals(Progress.DONE)) {
            String strTime = LocalDateTime.now().format(formatter);
            endTime = LocalDateTime.parse(strTime, formatter);
        }
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Progress getStatus() {
        return status;
    }

    public void setStatus(Progress status) {
        switch (status) {
            case IN_PROGRESS -> startTime = LocalDateTime.parse(LocalDateTime.now().format(formatter), formatter);
            case DONE -> endTime = LocalDateTime.now();
        }
        this.status = status;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String tasktoString() {
            return this.getId() + ","
                    + "TASK" + ","
                    + this.getName() + ","
                    + this.getStatus() + ","
                    + this.getDescription() + ","
                    + this.getStartTime() + ","
                    + this.getDuration();
    }

    public LocalDateTime getEndTime() {
            if (startTime != null && duration != null && endTime == null) {
                return startTime.plusMinutes(duration.toMinutes());
            } else if (endTime != null) {
                return endTime;
            } else {
                System.out.println("Ошибка при возвращении конеч. времени");
                return null;
            }
    }

    public LocalDateTime getStartTime() {
        if (startTime != null) {
            return startTime;
        } else {
            return null;
        }
    }

    public void setStartTime(LocalDateTime start) {
        this.startTime =  start;
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