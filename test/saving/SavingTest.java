package saving;

import manager.FileBackedTaskManager;
import org.junit.jupiter.api.Test;
import task.*;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class SavingTest {

    Epic epic = new Epic("name", Progress.NEW,"аапап");
    Subtask subtask1 = new Subtask("nameSub1", "opisanitdfdfdf", Progress.NEW);
    Task task = new Task("name1", Progress.NEW, "ndffdfdf");

    @Test
    public void globalTest() throws IOException, ManagerSaveException {
        Path tempFilePath = Files.createTempFile(null, ".txt");
        File file = new File(String.valueOf(tempFilePath));
        FileBackedTaskManager fileBackedTaskManager = new FileBackedTaskManager(file);
        fileBackedTaskManager.addTask(task);
        fileBackedTaskManager.addEpic(epic);
        subtask1.setEpicId(epic.getId());
        fileBackedTaskManager.createSubtask(subtask1);

        ArrayList<String> res = new ArrayList<>();
        res.add(task.TasktoString());
        res.add(epic.epictoString());
        res.add(subtask1.subTasktoString());

        fileBackedTaskManager.save();
        ArrayList<String> result = new ArrayList<>();
        try {
            BufferedReader reader = new BufferedReader(new FileReader(file));
            String line;
            reader.readLine();
                while ((line = reader.readLine()) != null) {
                    result.add(line);
                }
            } catch (Throwable exception) {
            System.out.println("Ошибка в тесте сохранения");
        }
        assertEquals(result, res); // первый тест

        fileBackedTaskManager.deleteAllTasks();
        fileBackedTaskManager.deleteAllEpics();
        fileBackedTaskManager.removeAllSubs();

        var fbt = FileBackedTaskManager.loadFromFile(file);
        assertEquals(fbt.getTasks().size(), 1); // второй тест
        assertEquals(fbt.getAllEpics().size(), 1);
        assertEquals(fbt.getAllSubs().size(), 1);
    }
}