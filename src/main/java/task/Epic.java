package task;

import java.util.ArrayList;
import java.util.List;

public class Epic extends Task {
    private final List<Subtask> subList = new ArrayList<>();

    public Epic(String name, String description) {
        super(name, description);
    }

    public List<Subtask> getAllSubTasks() {
        return new ArrayList<>(subList);
    }

    public void addSubTask(Subtask subtask) {
        subtask.setEpicId(this.getId());
        subList.add(subtask);
    }

    public void deleteSubTask(Subtask subtask) {
        for (Subtask subtask1 : subList) {
            if (subtask1.getId() == subtask.getId()) {
                subList.remove(subtask1);
                break;
            }
        }
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

    public String EpictoString(){
        return this.getName() + " " + this.getStatus() + " " + this.getDescription();
    }
}