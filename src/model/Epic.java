package model;

import java.util.ArrayList;

public class Epic extends Task {
    private  ArrayList <Subtask> subList = new ArrayList<>();

    public Epic(String name, String description) {
        super(name, description);
    }
    public ArrayList<Subtask> returnSub(){
        return subList;
    }
    public void addSubTask(Subtask subtask){
        subtask.setEpicId(this.getId());
        subList.add(subtask);
    }
    @Override
    public Progress getStatus(){
        Progress newStatus = calculateStatus();
        this.setStatus(newStatus);
        return newStatus;
    }

    private Progress calculateStatus(){
        boolean res = false;
        for (Subtask subtask : this.returnSub()){
            if ( subtask.getStatus() == Progress.NEW){
                res = true;
            }
            else {
                res = false;
                break;
            }
        }
        Progress resultStatus = Progress.NEW;
        if (this.returnSub() == null || res) {
            return resultStatus;
        }
        else {
            for (Subtask sub : this.returnSub()) {
                if (sub.getStatus() == Progress.DONE) {
                    resultStatus = Progress.DONE;
                }
                else {
                    resultStatus = Progress.IN_PROGRESS;
                    break;
                }
            }
        }
        return resultStatus;
    }
}