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
    public void removeSubTask(int id){
        int index = -1;
        for(Subtask sub : subList){
            if (sub.getId() == id){
                index = subList.indexOf(sub);
                break;
            }
        }
        if (index >= 0){
            subList.remove(index);
        }
    }
    public void removeAll(){
        subList.clear();

    }
    public ArrayList<Subtask> returnAllSubTasks(){
        return subList;
    }
    public void updateSubTask(Subtask subtask2, int idSub){
        for (Subtask subtask : subList){
            if (subtask.getId() == idSub){
                subtask.setName(subtask2.getName());
                subtask.setDescription(subtask2.getDescription());
                subtask.setStatus(subtask2.getStatus());
                break;
            }
        }
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
    public Epic copy(){
        Epic result = new Epic(this.getName(), this.getDescription());
        for (var subtask : this.subList){
            result.addSubTask(subtask.copy());
        }
        return result;
    }
}