import manager.ITaskManager;
import manager.InMemoryHistoryManager;
import manager.InMemoryTaskManager;
import task.*;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        ITaskManager taskManager  = Managers.getDefault();
        Task task1 = new Task("Починка двери", Progress.NEW, "");
        Task task2 = new Task("dfdf", Progress.NEW, "");

        Subtask subtask1 = new Subtask("Найти деньги", "", Progress.IN_PROGRESS);
        Subtask subtask2 = new Subtask("Найти людей", "", Progress.IN_PROGRESS);
        Subtask subtask3 = new Subtask("Найти что то ", "", Progress.DONE);

        Epic epic1 = new Epic("Первый эпик", "");
        Epic epic2 = new Epic("Второй эпик", "");

        epic2.setId(1);
        epic1.setId(2);

        taskManager.addEpic(epic1);
        taskManager.addEpic(epic2);

        taskManager.addTask(task1);
        taskManager.addTask(task2);

        taskManager.getTaskById(task1.getId());
        taskManager.getTaskById(task2.getId());


        System.out.println(taskManager.getHistory());
        taskManager.addEpic(epic1);
        taskManager.addEpic(epic2);

        System.out.println(taskManager.getAllEpics());
        epic1.addSubTask(subtask1);
        epic1.addSubTask(subtask2);

        System.out.println(taskManager.getAllEpicSubTasks(epic1.getId()));
        taskManager.removeAllSubs();

        System.out.println(epic1.getAllSubTasks());
    }
}
