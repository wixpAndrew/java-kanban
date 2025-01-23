package manager;

import org.junit.jupiter.api.Test;
import task.Epic;
import task.Managers;
import task.Progress;
import task.Subtask;


import static org.junit.jupiter.api.Assertions.assertEquals;

class EpicTest {
    ITaskManager taskManager = Managers.getDefault();

    @Test
    void add() {
        Epic epic = new Epic("name",  "");
        taskManager.addEpic(epic);
        taskManager.getEpicById(1);
        assertEquals(1, taskManager.getEpics().size());
    }

    @Test
    void checkClear() {
        Epic epic1 = new Epic("name1",  "dsdsds");
        Epic epic2 = new Epic("name2",  "");
        taskManager.addEpic(epic1);
        taskManager.addEpic(epic2);
        taskManager.deleteAllEpics();
        assertEquals(0, taskManager.getEpics().size());
    }

    @Test
    void checkStatusWhenNewAll() {
        Subtask subtask1 = new Subtask("dfdf", "sd", Progress.NEW);
        Subtask subtask2 = new Subtask("ddffdfd", "sd", Progress.NEW);
        Subtask subtask3 = new Subtask("fdfdfddf", "sd", Progress.NEW);

        Epic epic = new Epic("ddd", "ppp");

        epic.addSubTask(subtask1);
        epic.addSubTask(subtask2);
        epic.addSubTask(subtask3);

        epic.calculateStatus();
        assertEquals(Progress.NEW ,epic.getStatus());
    }

    @Test
    void checkStatusWhenDoneAll() {
        Subtask subtask1 = new Subtask("dfdf", "sd", Progress.DONE);
        Subtask subtask2 = new Subtask("ddffdfd", "sd", Progress.DONE);
        Subtask subtask3 = new Subtask("fdfdfddf", "sd", Progress.DONE);

        Epic epic = new Epic("ddd", "ppp");

        epic.addSubTask(subtask1);
        epic.addSubTask(subtask2);
        epic.addSubTask(subtask3);

        epic.calculateStatus();
        assertEquals(Progress.DONE ,epic.getStatus());
    }

    @Test
    void checkStatusWhenNewDone() {
        Subtask subtask1 = new Subtask("dfdf", "sd", Progress.NEW);
        Subtask subtask2 = new Subtask("ddffdfd", "sd", Progress.NEW);
        Subtask subtask3 = new Subtask("fdfdfddf", "sd", Progress.DONE);

        Epic epic = new Epic("ddd", "ppp");

        epic.addSubTask(subtask1);
        epic.addSubTask(subtask2);
        epic.addSubTask(subtask3);

        epic.calculateStatus();
        assertEquals(Progress.IN_PROGRESS ,epic.getStatus());
    }

    @Test
    void checkStatusWhenProgress() {
        Subtask subtask1 = new Subtask("dfdf", "sd", Progress.IN_PROGRESS);
        Subtask subtask2 = new Subtask("ddffdfd", "sd", Progress.IN_PROGRESS);
        Subtask subtask3 = new Subtask("fdfdfddf", "sd", Progress.IN_PROGRESS);

        Epic epic = new Epic("ddd", "ppp");

        epic.addSubTask(subtask1);
        epic.addSubTask(subtask2);
        epic.addSubTask(subtask3);

        epic.calculateStatus();
        assertEquals(Progress.IN_PROGRESS ,epic.getStatus());
    }
}