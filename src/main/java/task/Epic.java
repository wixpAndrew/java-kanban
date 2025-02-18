package task;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;

public class Epic extends Task {
    private final List<Subtask> subList = new ArrayList<>();

    public Epic(String name, String description) {
        super(name, null, description);
    }

    public List<Subtask> getAllSubTasks() {
        return new ArrayList<>(subList);
    }

    public void addSubTask(Subtask subtask) {
        if (subtask.getStatus() == Progress.IN_PROGRESS && subList.isEmpty()) {
            LocalDateTime startTime = subtask.getStartTime();
        }
        subtask.setEpicId(this.getId());
        subList.add(subtask);
    }

    public void deleteSubTask(Subtask subtask) {
        subList.remove(subtask);
    }

    public void calculateStatus() {
        Progress finalProgress;
        boolean isNew = false;
        boolean isInProgress = false;
        boolean isDone = false;

        if (this.getAllSubTasks() != null) {
            for (Subtask subtask : this.getAllSubTasks()) {
                if (subtask.getStatus() == Progress.IN_PROGRESS) {
                    isInProgress = true;
                } else if (subtask.getStatus() == Progress.DONE) {
                    isDone = true;
                } else {
                    isNew = true;
                }
            }
            if (isNew && !isDone && !isInProgress) {
                finalProgress = Progress.NEW;
            } else if (isDone && !isNew && !isInProgress) {
                finalProgress = Progress.DONE;
            } else {
                finalProgress = Progress.IN_PROGRESS;
            }
        } else {
            finalProgress = Progress.NEW;
        }
        this.setStatus(finalProgress);
    }

    public void deleteAllSubTasks() {
        subList.clear();
    }

    public String epictoString() {
        return this.getId() + "," + "EPIC" + "," +
                this.getName() + "," +
                this.getStatus() + "," +
                this.getDescription() + "," +
                this.getStartTime() + "," +
                this.getDuration();
    }

    public LocalDateTime getEndTime() {
        LocalDateTime result;
        if (subList.isEmpty()) return null;
        result = subList.get(0).getStartTime();
        for (int i = 1; i < subList.size(); i++) {
            if (result.isAfter(subList.get(i).getStartTime())) {
                result = subList.get(i).getStartTime();
            }
        }
        return result;
    }

    public LocalDateTime getStartTime() {
        if (subList.isEmpty()) return null;

        List<LocalDateTime> listOfDateTime = new ArrayList<>();

        subList.stream()
                .filter(v -> v != null && v.getStartTime() != null)
                .forEach(v -> listOfDateTime.add(v.getStartTime()));

        Comparator<LocalDateTime> localDateTimeComparator = LocalDateTime::compareTo;
        LocalDateTime minDate = listOfDateTime.stream()
                .min(localDateTimeComparator)
                .orElse(null);
        return minDate;
    }
}