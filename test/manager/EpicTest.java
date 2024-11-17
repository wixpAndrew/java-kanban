package manager;

import org.junit.jupiter.api.Test;
import task.Epic;
import task.Managers;
import task.Progress;


import static org.junit.jupiter.api.Assertions.assertEquals;

class EpicTest {
    ITaskManager taskManager = Managers.getDefault();

    @Test
    void add() {
        Epic epic = new Epic("name", Progress.NEW, "");
        taskManager.addEpic(epic);
        taskManager.getEpicById(1);
        assertEquals(1, taskManager.getAllEpics().size());
    }

    @Test
    void checkClear() {
        Epic epic1 = new Epic("name1", Progress.NEW, "dsdsds");
        Epic epic2 = new Epic("name2", Progress.NEW, "");
        taskManager.addEpic(epic1);
        taskManager.addEpic(epic2);
        taskManager.deleteAllEpics();
        assertEquals(0, taskManager.getAllEpics().size());
    }
}