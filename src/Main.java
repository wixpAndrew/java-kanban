import controllers.TaskManager;
import model.Epic;
import model.Progress;
import model.Subtask;
import model.Task;

import java.util.ArrayList;
//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        TaskManager tm = new TaskManager();
        Task task1 = new Task("Починка двери", 1, Progress.NEW, "");
        Task task2 = new Task("Покупка дома", 2 , Progress.NEW, "");
        Subtask subtask1 = new Subtask("Найти деньги", "",Progress.NEW, 3 );
        Subtask subtask2 = new Subtask("Найти людей", "",Progress.NEW, 4);
        Subtask subtask3 = new Subtask("Найти что то ", "",Progress.DONE, 5);
        Epic epic1 = new Epic("Первый эпик", "", 6);
        Epic epic2 = new Epic("Второй эпик", "", 7);

        epic2.addSubTask(subtask1);
        epic2.addSubTask(subtask2);
        epic1.addSubTask(subtask3);

        tm.addEpic(epic1);
        tm.addEpic(epic2);
        System.out.println(epic1.getStatus());
        subtask3.setStatus(Progress.NEW);
        System.out.println(epic1.getStatus());
        tm.deleteEpic(1);
        System.out.println();

    }
}