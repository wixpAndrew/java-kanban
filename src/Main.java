import controllers.InMemoryTaskManager;
import model.Epic;
import model.Progress;
import model.Subtask;
import model.Task;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        InMemoryTaskManager tm = new InMemoryTaskManager();
        Task task1 = new Task("Починка двери", Progress.NEW, "");
        Task task2 = task1;
        Subtask subtask1 = new Subtask("Найти деньги", "",Progress.NEW );
        Subtask subtask2 = new Subtask("Найти людей", "",Progress.NEW);
        Subtask subtask3 = new Subtask("Найти что то ", "",Progress.DONE);
        Epic epic1 = new Epic("Первый эпик", "");
        Epic epic2 = new Epic("Второй эпик", "");
        epic2.setId(1);
        epic1.setId(2);
        epic2.addSubTask(subtask1);
        epic2.addSubTask(subtask2);
        epic1.addSubTask(subtask3);

        tm.addEpic(epic1);
        tm.addEpic(epic2);
        subtask3.setStatus(Progress.NEW);
        tm.deleteEpic(1);

        task1.setId(tm.generateId());
        System.out.println(task1.getId());
        System.out.println(task2.getId());
    }
}