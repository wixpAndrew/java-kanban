package task;

import manager.ITaskManager;
import manager.InMemoryTaskManager;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TaskTest {

    @Test
    void checkAddTask() {
        ITaskManager inMemoryTaskManager = Managers.getDefault();
        Task task = new Task("name1", "ndffdfdf");
        inMemoryTaskManager.addTask(task);
        List<Task> ar = new ArrayList<>();
        ar.add(task);
        assertEquals(inMemoryTaskManager.getTasks(), ar);
    }

    @Test
    void getTaskBtId() {
        ITaskManager inMemoryTaskManager = Managers.getDefault();
        Task task = new Task("name1", "ndffdfdf");
        inMemoryTaskManager.addTask(task);
        assertEquals(inMemoryTaskManager.getTaskById(task.getId()), task);
    }

    @Test
    void checkUpdateTask() {
        ITaskManager inMemoryTaskManager = Managers.getDefault();
        Task task1 = new Task("name1", "ndffdfdf");
        Task task2 = new Task("name2", "ndffdfdf");
        inMemoryTaskManager.addTask(task1);
        task2.setId(task1.getId());
        inMemoryTaskManager.updateTask(task2);

       assertEquals(inMemoryTaskManager.getTaskById(task1.getId()), task2);
    }
}
