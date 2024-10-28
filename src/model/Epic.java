package model;

import java.util.ArrayList;

public class Epic extends Task {
    private ArrayList<Subtask> subList = new ArrayList<>();

    public Epic(String name, String description) {
        super(name, description);
    }

    public ArrayList<Subtask> getAllSubTasks() {
        if (subList.isEmpty()) {
            return subList;
        } else {
            return new ArrayList<>();
        }
    }

    public void addSubTask(Subtask subtask) {
        subList.add(subtask);;
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
            if (isNew == true && isDone == false && isInProgress == false) {
                finalProgress = Progress.NEW;
            } else if (isDone == true && isNew == false && isInProgress == false) {
                finalProgress = Progress.DONE;
            } else {
                finalProgress = Progress.IN_PROGRESS;
            }
        } else {
            finalProgress = Progress.NEW;
        }
        this.setStatus(finalProgress);
    }
}