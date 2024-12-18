package task;

import jdk.jfr.DataAmount;
import java.sql.SQLOutput;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Task {

    private String name;
    private Progress status;
    private Integer id;
    private String description;
    LocalDateTime startTime;
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
    Duration duration;
    LocalDateTime endTime;

    public Task(String name, Progress status, String description) {
        this.name = name;
        this.status = status;
        this.description = description;

        if (status == null) {
            System.out.println("Ошибка , статус == null");
        } else if (status.equals(Progress.IN_PROGRESS)) {
            String strTime = LocalDateTime.now().format(formatter);
            startTime= LocalDateTime.parse(strTime, formatter);
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
            return this.getId() + "," + "TASK" + "," + this.getName() + "," + this.getStatus() + "," + this.getDescription() + ",";
    }
    public LocalDateTime getEndTime() {
            if (startTime != null && duration != null) {
                return startTime.plusMinutes(duration.toMinutes());
            }
            return null;
    }

    public LocalDateTime getStartTime() {
        if (startTime != null) {
            return startTime;
        }
        else {
            System.out.println("Ошибка при выводе старта !");
        }
        return null;
    }
}