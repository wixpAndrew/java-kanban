package history;

import manager.ITaskManager;
import org.junit.jupiter.api.Test;
import task.Managers;
import task.Task;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class HistoryListTest {

    private final Task task1 = new Task("sf", "sfdf");
    private final Task task2 = new Task("aaaa", "ffff");
    private final Task task3 = new Task("theiii", "dggg");
    private final ITaskManager taskManger = Managers.getDefault();

    @Test
    public void checkAddFunction() {

        taskManger.addTask(task1);
        taskManger.addTask(task2);
        taskManger.addTask(task3);

        taskManger.getTaskById(task1.getId());
        taskManger.getTaskById(task2.getId());

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
        taskManger.deleteTask(task2.getId());
       assertEquals(2, taskManger.getHistory().size());
    }
}
