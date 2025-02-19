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
    private final Subtask subtask1 = new Subtask("nameSub1", "opisanitdfdfdf", Progress.IN_PROGRESS);
    private final Subtask subtask2 = new Subtask("namesub2", "dfdfdf", Progress.IN_PROGRESS);
    private final Task task = new Task("name1", Progress.NEW, "ndffdfdf");

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

        fileBackedTaskManager.save();
        FileBackedTaskManager.loadFromFile(file);

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

        assertEquals(result.get(0), fileBackedTaskManager.getTasks().get(0).tasktoString());
        assertEquals(result.get(1), fileBackedTaskManager.getEpics().get(0).epictoString());
        assertEquals(result.get(2), fileBackedTaskManager.getAllSubs().get(0).subTasktoString());
        assertEquals(fileBackedTaskManager.getEpics().size(), 1);
        assertEquals(fileBackedTaskManager.getTasks().size(), 1);
        assertEquals(fileBackedTaskManager.getTasks().size(), 1);
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


        fileBackedTaskManager.save();
        FileBackedTaskManager.loadFromFile(file);

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
        assertEquals(result.get(0), fileBackedTaskManager.getTasks().get(0).tasktoString());
        assertEquals(result.get(1), fileBackedTaskManager.getEpics().get(0).epictoString());
        assertEquals(result.get(2), fileBackedTaskManager.getEpics().get(1).epictoString());
        assertEquals(result.get(3), fileBackedTaskManager.getAllSubs().get(0).subTasktoString());
        assertEquals(result.get(4), fileBackedTaskManager.getAllSubs().get(1).subTasktoString());
        assertEquals(fileBackedTaskManager.getEpics().size(), 2);
        assertEquals(fileBackedTaskManager.getAllSubs().size(), 2);
        assertEquals(fileBackedTaskManager.getTasks().size(), 1);
    }
    @Test
    public void checkEmptyFileLoad() throws IOException, ManagerSaveException {
        Path tempFilePath = Files.createTempFile(null, ".txt");
        File file = new File(String.valueOf(tempFilePath));
        FileBackedTaskManager fileBackedTaskManager = new FileBackedTaskManager(file);

        fileBackedTaskManager.save();
        FileBackedTaskManager.loadFromFile(file);

        assertEquals(fileBackedTaskManager.getEpics(), new ArrayList<>());
        assertEquals(fileBackedTaskManager.getTasks(), new ArrayList<>());
        assertEquals(fileBackedTaskManager.getAllSubs(), new ArrayList<>());

    }
}