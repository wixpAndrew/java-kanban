package history;

import manager.ITaskManager;
import manager.InMemoryHistoryManager;
import manager.InMemoryTaskManager;
import org.junit.jupiter.api.Test;
import task.Managers;
import task.Task;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class HistoryListTest {

    private Task task1 = new Task("sf", "sfdf");
    private Task task2 = new Task("aaaa", "ffff");
    private Task task3 = new Task("theiii", "dggg");
    private ITaskManager taskManger = Managers.getDefault();
    private List<Task> result = new ArrayList<>();

    @Test
    public void checkAddFunction() {

        taskManger.addTask(task1);
        taskManger.addTask(task2);
        taskManger.addTask(task3);

        taskManger.getTaskById(task1.getId());
        taskManger.getTaskById(task2.getId());

        result.add(task1);
        result.add(task2);

        assertEquals(2, taskManger.getHistory().size());
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
       assertEquals(2, taskManger.getHistory().size());
    }
}
