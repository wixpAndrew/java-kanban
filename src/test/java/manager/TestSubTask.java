import manager.InMemoryTaskManager;
import org.junit.jupiter.api.Test;
import task.Epic;
import task.Progress;
import task.Subtask;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestSubTask {

    @Test
    void checkSubTaskById() {
        InMemoryTaskManager inMemoryTaskManager = new InMemoryTaskManager();
        Epic epic1 = new Epic("name1", "dsdsds");
        Subtask subtask = new Subtask("nameSub", "opisanit", Progress.NEW);
        inMemoryTaskManager.addEpic(epic1);
        epic1.addSubTask(subtask);
        inMemoryTaskManager.createSubtask(subtask);
        assertEquals(inMemoryTaskManager.getSubtaskById(subtask.getId()), subtask);
    }

    @Test
    void checkReturnAllSubTasks() {
        InMemoryTaskManager inMemoryTaskManager = new InMemoryTaskManager();
        Epic epic2 = new Epic("name1", "dsdsds");
        Subtask subtask1 = new Subtask("nameSub1", "opisanitdfdfdf", Progress.NEW);
        Subtask subtask2 = new Subtask("nameSub2", "opisanitdfdf", Progress.NEW);

        inMemoryTaskManager.addEpic(epic2);
        subtask1.setEpicId(epic2.getId());
        subtask2.setEpicId(epic2.getId());

        inMemoryTaskManager.createSubtask(subtask1);
        inMemoryTaskManager.createSubtask(subtask2);

        ArrayList<Subtask> ar = new ArrayList<>();
        ar.add(subtask1);
        ar.add(subtask2);

        assertEquals(ar, inMemoryTaskManager.getAllEpicSubTasks(epic2.getId()));
    }

    @Test
    void checkRemoveAllSubTasks() {
        InMemoryTaskManager inMemoryTaskManager = new InMemoryTaskManager();
        Epic epic1 = new Epic("name1", "dsdsds");
        Subtask subtask1 = new Subtask("nameSub1", "opisanitdfdfdf", Progress.NEW);
        Subtask subtask2 = new Subtask("nameSub2", "opisanitdfdf", Progress.NEW);

        inMemoryTaskManager.addEpic(epic1);
        subtask1.setEpicId(epic1.getId());
        subtask2.setEpicId(epic1.getId());

        inMemoryTaskManager.createSubtask(subtask1);
        inMemoryTaskManager.createSubtask(subtask2);
        inMemoryTaskManager.removeAllSubs();

        ArrayList<Subtask> ar = new ArrayList<>();
        assertEquals(inMemoryTaskManager.getAllEpicSubTasks(epic1.getId()), ar);
    }
}