package manager;

import org.junit.jupiter.api.Test;
import task.Epic;
import task.Managers;

import java.util.ArrayList;
import java.util.Iterator;

import static org.junit.jupiter.api.Assertions.assertEquals;

class EpicTest {
    @Test
    void add() {
        HistoryManager historyM = Managers.getDefaultHistory();
        Epic epic = new Epic("name", "dfd");
        ITaskManager taskManager1 = Managers.getDefault();
        taskManager1.addEpic(epic);
        taskManager1.getEpicById(1);
        assertEquals(1, taskManager1.getAllEpics().size());
    }

    @Test
    void checkClear() {
        ITaskManager inMemoryTaskManager2 = Managers.getDefault();
        Epic epic1 = new Epic("name1", "dsdsds");
        Epic epic2 = new Epic("name2", "dfdfdfd");
        inMemoryTaskManager2.addEpic(epic1);
        inMemoryTaskManager2.addEpic(epic2);
        inMemoryTaskManager2.deleteAllEpics();
        ArrayList<Epic> ar = new ArrayList<>();
        assertEquals(0, inMemoryTaskManager2.getAllEpics().size());
    }
}