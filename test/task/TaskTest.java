package task;

import manager.ITaskManager;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TaskTest {
    private final ITaskManager inMemoryTaskManager = Managers.getDefault();
    @Test
    void checkAddTask() {
        Task task = new Task("name1",Progress.NEW, "ndffdfdf");
        inMemoryTaskManager.addTask(task);
        List<Task> ar = new ArrayList<>();
        ar.add(task);
        assertEquals(inMemoryTaskManager.getTasks(), ar);
    }

    @Test
    void getTaskBtId() {
        Task task = new Task("name1", Progress.NEW, "ndffdfdf");
        inMemoryTaskManager.addTask(task);
        assertEquals(inMemoryTaskManager.getTaskById(task.getId()), task);
    }

    @Test
    void checkUpdateTask() {
        Task task1 = new Task("name1", Progress.NEW, "");
        Task task2 = new Task("name2", Progress.NEW, "");
        inMemoryTaskManager.addTask(task1);
        task2.setId(task1.getId());
        inMemoryTaskManager.updateTask(task2);

       assertEquals(inMemoryTaskManager.getTaskById(task1.getId()), task2);
    }
}
