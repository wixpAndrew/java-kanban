package tests;
import controllers.InMemoryHistoryManager;
import controllers.InMemoryTaskManager;
import model.Task;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class HistoryListTest {
    @Test
    public void checkAddFunction(){
        Task task1 = new Task("sf", "sfdf");
        Task task2 = new Task("aaaa", "ffff");
        Task task3 = new Task("theiii", "dggg");

        InMemoryTaskManager taskManger = new InMemoryTaskManager();
        InMemoryHistoryManager history = new InMemoryHistoryManager();
        List<Task> result = new ArrayList<>();
        taskManger.addTask(task1);
        taskManger.addTask(task2);
        taskManger.addTask(task3);

        taskManger.getTaskById(task1.getId());
        taskManger.getTaskById(task2.getId());

        result.add(task1);
        result.add(task2);

        assertEquals(taskManger.getHistory(), result);
    }
    @Test
    public void checkRemoveFuction(){
        Task task1 = new Task("sf", "sfdf");
        Task task2 = new Task("aaaa", "ffff");
        Task task3 = new Task("theiii", "dggg");

        InMemoryTaskManager taskManger = new InMemoryTaskManager();
        InMemoryHistoryManager history = new InMemoryHistoryManager();
        List<Task> result = new ArrayList<>();
        taskManger.addTask(task1);
        taskManger.addTask(task2);
        taskManger.addTask(task3);

        taskManger.getTaskById(task1.getId());
        taskManger.getTaskById(task2.getId());
        taskManger.getTaskById(task3.getId());

        result.add(task1);
        result.add(task3);

        taskManger.deleteTask(task2.getId());
        assertEquals(taskManger.getHistory(), result);
    }
}
