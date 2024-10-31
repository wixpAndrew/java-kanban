package history;

import manager.InMemoryHistoryManager;
import manager.InMemoryTaskManager;
import org.junit.jupiter.api.Test;
import task.Task;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class HistoryListTest {

    public Task task1 = new Task("sf", "sfdf");
    public Task task2 = new Task("aaaa", "ffff");
    public Task task3 = new Task("theiii", "dggg");
    InMemoryTaskManager taskManger = new InMemoryTaskManager();
    InMemoryHistoryManager history = new InMemoryHistoryManager();
    List<Task> result = new ArrayList<>();

    @Test
    public void checkAddFunction() {

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
    public void checkRemoveFuction() {

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
