package model;

import java.util.ArrayList;

public class Epic extends Task {
    private ArrayList<Subtask> subList = new ArrayList<>();

    public Epic(String name, String description) {
        super(name, description);
    }

    public ArrayList<Subtask> returnSub() {
        return subList;
    }

    public void addSubTask(Subtask subtask) {
        subList.add(subtask);
    }

    public void calculateStatus() {
        Progress finalProgress;
        boolean isNew = false;
        boolean isInProgress = false;
        boolean isDone = false;
        if (this.returnSub() != null){
            for(Subtask subtask : this.returnSub()){
                if (subtask.getStatus() == Progress.IN_PROGRESS){
                    isInProgress = true;
                }
                else if (subtask.getStatus() == Progress.DONE){
                        isDone = true;
                }
                else {
                    isNew = true;
                }
            }
            if (isNew == true && isDone == false && isInProgress == false ){
                finalProgress = Progress.NEW;
            }
            else if( isDone == true && isNew == false && isInProgress == false){
                finalProgress = Progress.DONE;
            }
            else {
                finalProgress = Progress.IN_PROGRESS;
            }
        }
        else {
            finalProgress = Progress.NEW;
        }
        this.setStatus(finalProgress);
    }
}