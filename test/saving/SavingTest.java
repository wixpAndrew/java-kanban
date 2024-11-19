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

    private final Epic epic = new Epic("name", "аапап");
    private final Epic epic2 = new Epic("nam2", "dddd");
    private final Subtask subtask1 = new Subtask("nameSub1", "opisanitdfdfdf", Progress.NEW);
    private final Subtask subtask2 = new Subtask("namesub2", "dfdfdf", Progress.NEW);
    private final Task task = new Task("name1", Progress.NEW, "ndffdfdf");
    private final Task task2 = new Task("name4", Progress.NEW, "pppppp");


    @Test
    public void testWithOneTypesOfTask() throws IOException, ManagerSaveException {
        Path tempFilePath = Files.createTempFile(null, ".txt");
        File file = new File(String.valueOf(tempFilePath));
        FileBackedTaskManager fileBackedTaskManager = new FileBackedTaskManager(file);
        epic.setStatus(Progress.NEW);
        fileBackedTaskManager.addTask(task);
        fileBackedTaskManager.addEpic(epic);
        subtask1.setEpicId(epic.getId());
        fileBackedTaskManager.addSubtask(subtask1);

        ArrayList<String> res = new ArrayList<>();
        res.add(task.tasktoString());
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
    @Test
    public void testWithALotOfTypes() throws IOException, ManagerSaveException {
        Path tempFilePath = Files.createTempFile(null, ".txt");
        File file = new File(String.valueOf(tempFilePath));
        FileBackedTaskManager fileBackedTaskManager = new FileBackedTaskManager(file);
        epic2.setStatus(Progress.NEW);
        epic.setStatus(Progress.NEW);
        fileBackedTaskManager.addTask(task);
        fileBackedTaskManager.addEpic(epic);
        subtask1.setEpicId(epic.getId());
        fileBackedTaskManager.addSubtask(subtask1);
        fileBackedTaskManager.addEpic(epic2);
        subtask2.setEpicId(epic2.getId());
        fileBackedTaskManager.addSubtask(subtask2);


        ArrayList<String> res = new ArrayList<>();
        res.add(task.tasktoString());
        res.add(epic.epictoString());
        res.add(epic2.epictoString());
        res.add(subtask1.subTasktoString());
        res.add(subtask2.subTasktoString());

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
        assertEquals(fbt.getAllEpics().size(), 2);
        assertEquals(fbt.getAllSubs().size(), 2);
    }
}