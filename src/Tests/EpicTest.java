package tests;

import controllers.*;
import model.Epic;

import org.junit.jupiter.api.Test;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;
class EpicTest {
    @Test
    void add() {
        HistoryManager historyM = Managers.getDefaultHistory();
        Epic epic = new Epic("name", "dfd");
       ITaskManager taskManager = Managers.getDefault();
        taskManager.addEpic(epic);
        taskManager.getEpicById(1);
        assertEquals(taskManager.getAllEpics().size(),  1);
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
            assertEquals(inMemoryTaskManager.getAllEpics(),ar);
    }
}