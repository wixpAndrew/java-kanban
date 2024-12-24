import manager.ITaskManager;
import task.*;

public class Main {
    public static void main(String[] args) {
        ITaskManager taskManager  = Managers.getDefault();

        Subtask subtask1 = new Subtask("Найти деньги", "", Progress.IN_PROGRESS);
        Subtask subtask2 = new Subtask("Найти людей", "", Progress.IN_PROGRESS);
        Subtask subtask3 = new Subtask("Найти что то ", "", Progress.DONE);

        Epic epic2 = new Epic("Второй эпик",  "");

       epic2.setId(1);

       epic2.addSubTask(subtask1);
       epic2.addSubTask(subtask2);

        //System.out.println(subtask2.getEndTime());
        //System.out.println(epic2.getEndTime());
        System.out.println("----------------------------------");
        System.out.println(epic2.getStartTime());
        System.out.println(epic2.getEndTime());
        System.out.println(subtask1.getStartTime());
    }
}