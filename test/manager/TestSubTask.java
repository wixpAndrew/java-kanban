package manager;

import org.junit.jupiter.api.Test;
import task.Epic;
import task.Managers;
import task.Progress;
import task.Subtask;


import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestSubTask {
    ITaskManager inMemoryTaskManager = Managers.getDefault();

    @Test
    void checkSubTaskById() {
        Epic epic1 = new Epic("name1",  "");
        Subtask subtask = new Subtask("nameSub", "opisanit", Progress.NEW);
        inMemoryTaskManager.addEpic(epic1);
        epic1.addSubTask(subtask);
        inMemoryTaskManager.addSubtask(subtask);
        assertEquals(subtask, inMemoryTaskManager.getSubtaskById(subtask.getId()));
    }

    @Test
    void checkReturnAllSubTasks() {
        Epic epic2 = new Epic("name1", "dsdsds");
        Subtask subtask1 = new Subtask("nameSub1", "opisanitdfdfdf", Progress.NEW);
        Subtask subtask2 = new Subtask("nameSub2", "opisanitdfdf", Progress.NEW);

        inMemoryTaskManager.addEpic(epic2);
        subtask1.setEpicId(epic2.getId());
        subtask2.setEpicId(epic2.getId());

        inMemoryTaskManager.addSubtask(subtask1);
        inMemoryTaskManager.addSubtask(subtask2);

       assertEquals(2, inMemoryTaskManager.getAllEpicSubTasks(epic2.getId()).size());
    }

    @Test
    void checkRemoveAllSubTasks() {
        Epic epic1 = new Epic("name1", "");
        Subtask subtask1 = new Subtask("nameSub1", "opisanitdfdfdf", Progress.NEW);
        Subtask subtask2 = new Subtask("nameSub2", "opisanitdfdf", Progress.NEW);
        inMemoryTaskManager.addEpic(epic1);
        subtask1.setEpicId(epic1.getId());
        subtask2.setEpicId(epic1.getId());

        inMemoryTaskManager.addSubtask(subtask1);
        inMemoryTaskManager.addSubtask(subtask2);
        inMemoryTaskManager.deleteSubtasks();

        assertEquals(0, inMemoryTaskManager.getAllEpicSubTasks(epic1.getId()).size());
    }
}