package manager;

import org.junit.jupiter.api.Test;
import task.Epic;
import task.Managers;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;

class EpicTest {
    @Test
    void add() {
        HistoryManager historyM = Managers.getDefaultHistory();
        Epic epic = new Epic("name", "dfd");
        ITaskManager taskManager = Managers.getDefault();
        taskManager.addEpic(epic);
        taskManager.getEpicById(1);
        assertEquals(1, taskManager.getAllEpics().size());
    }

    @Test
    void checkClear() {
        InMemoryTaskManager inMemoryTaskManager = new InMemoryTaskManager();
        Epic epic1 = new Epic("name1", "dsdsds");
        Epic epic2 = new Epic("name2", "dfdfdfd");
        inMemoryTaskManager.addEpic(epic1);
        inMemoryTaskManager.addEpic(epic2);
        inMemoryTaskManager.deleteAllEpics();
        ArrayList<Epic> ar = new ArrayList<>();
        assertEquals(inMemoryTaskManager.getAllEpics(), ar);
    }
}