package HistoryManager;

import manager.HistoryManager;
import manager.InMemoryHistoryManager;
import org.junit.jupiter.api.Test;
import task.Progress;
import task.Task;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class HistoryManagetTest {
    HistoryManager historyManager = new InMemoryHistoryManager();

    @Test
    void checkEmptyHistory() {
        assertEquals(new ArrayList<>(), historyManager.getHistory());
    }
    @Test
    void checkDeleting() {
        Task task1 = new Task("sfd", Progress.NEW, "sss");
        Task task2 = new Task("ssss", Progress.NEW, "sss");
        Task task3 = new Task("44444", Progress.DONE, "sss");
        Task task4 = new Task("вававава", Progress.DONE, "sss");
        task1.setId(1);
        task2.setId(2);
        task3.setId(3);
        task4.setId(4);

        historyManager.add(task1);
        historyManager.add(task2);
        historyManager.add(task3);
        historyManager.add(task4);

        historyManager.remove(2);
        List<Task> res = new ArrayList<>();
        res.add(task1);
        res.add(task3);
        res.add(task4);

        assertEquals(res, historyManager.getHistory());// удаление из середины

        historyManager.remove(1);
        res.remove(task1);
        assertEquals(res, historyManager.getHistory());// удаление из начала
    }
}