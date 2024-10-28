import controllers.InMemoryHistoryManager;
import controllers.InMemoryTaskManager;
import model.Epic;
import model.Progress;
import model.Subtask;
import model.Task;

import java.util.ArrayList;
import java.util.List;


//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        InMemoryTaskManager tm = new InMemoryTaskManager();
        InMemoryHistoryManager his = new InMemoryHistoryManager();
        Task task1 = new Task("Починка двери", Progress.NEW, "");
        Task task2 = new Task("dfdf", Progress.NEW, "");
        Subtask subtask1 = new Subtask("Найти деньги", "", Progress.IN_PROGRESS );
        Subtask subtask2 = new Subtask("Найти людей", "", Progress.IN_PROGRESS );
        Subtask subtask3 = new Subtask("Найти что то ", "", Progress.DONE );
        Epic epic1 = new Epic("Первый эпик", "");
        Epic epic2 = new Epic("Второй эпик", "");
        epic2.setId(1);
        epic1.setId(2);
        tm.addEpic(epic1);
        tm.addEpic(epic2);

        InMemoryHistoryManager history = new InMemoryHistoryManager();
        InMemoryTaskManager taskManager = new InMemoryTaskManager();
        List<Task> result = new ArrayList<>();
        taskManager.addTask(task1);
        taskManager.addTask(task2);

        taskManager.getTaskById(task1.getId());
        taskManager.getTaskById(task2.getId());

        result.add(task1);
        result.add(task2);

        System.out.println(taskManager.getHistory());

    }
}
