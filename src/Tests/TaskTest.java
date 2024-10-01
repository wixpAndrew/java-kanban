package tests;

import controllers.*;
import model.Epic;
import model.Progress;
import model.Subtask;
import model.Task;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TaskTest {
    @Test
    void checkAddTask(){
        InMemoryTaskManager inMemoryTaskManager = new InMemoryTaskManager();
        Task task = new Task("name1", "ndffdfdf");
        inMemoryTaskManager.addTask(task);
        ArrayList<Task> ar = new ArrayList<>();
        ar.add(task);
        assertEquals(inMemoryTaskManager.getTasks(), ar);
    }
    @Test
    void getTaskBtId(){
        InMemoryTaskManager inMemoryTaskManager = new InMemoryTaskManager();
        Task task = new Task("name1", "ndffdfdf");
        inMemoryTaskManager.addTask(task);
        assertEquals(inMemoryTaskManager.getTaskById(task.getId()), task);
    }
    @Test
    void checkUpdateTask(){
        InMemoryTaskManager inMemoryTaskManager = new InMemoryTaskManager();
        Task task1 = new Task("name1", "ndffdfdf");
        Task task2 = new Task("name2", "ndffdfdf");
        task2.setId(8);
        inMemoryTaskManager.addTask(task1);

        inMemoryTaskManager.updateTask(task1.getId(), task2);

        assertEquals(inMemoryTaskManager.getTaskById(task1.getId()), task2);
    }
}
