package Tests;

import controllers.*;
import model.Epic;
import model.Progress;
import model.Task;
import org.junit.jupiter.api.Test;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
class EpicTest {
    @Test
    void add() {
        HistoryManager historyM = Managers.getDefaultHistory();
        Epic epic = new Epic("name", "dfd");
       ITaskManager taskManager = Managers.getDefault();
        taskManager.addEpic(epic);
        taskManager.getEpicById(1);
        assertEquals(historyM.getHistory().size(), 1);
    }
}