package server;

import com.google.gson.*;
import manager.ITaskManager;
import manager.InMemoryTaskManager;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import task.Epic;
import task.Progress;
import task.Subtask;
import task.Task;
import java.io.IOException;
import java.lang.reflect.Type;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.Arrays;
import java.util.List;

import com.google.gson.Gson;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static task.Progress.IN_PROGRESS;

public class HttpTaskServerTest {

    private static final String path = "http://localhost:8080";
    ITaskManager taskManager = new InMemoryTaskManager();
    HttpTaskServer taskServer = new HttpTaskServer(taskManager);

    Gson gson = new GsonBuilder()
            .excludeFieldsWithoutExposeAnnotation()
            .registerTypeAdapter(LocalDateTime.class, new JsonDeserializer<LocalDateTime>() {
                @Override
                public LocalDateTime deserialize(JsonElement json, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
                    return ZonedDateTime.parse(json.getAsJsonPrimitive().getAsString()).toLocalDateTime();
                }
            })
            .registerTypeAdapter(LocalDateTime.class, new JsonSerializer<LocalDateTime>() {
                @Override
                public JsonElement serialize(LocalDateTime src, Type typeOfSrc, JsonSerializationContext context) {
                    return new JsonPrimitive(src.toString()); // Преобразуем LocalDateTime в строку ISO
                }
            })
            .registerTypeAdapter(Duration.class, new JsonDeserializer<Duration>() {
                @Override
                public Duration deserialize(JsonElement json, Type type, JsonDeserializationContext context) throws JsonParseException {
                    return Duration.parse(json.getAsString()); // Преобразуем строку в Duration
                }
            })
            .registerTypeAdapter(Duration.class, new JsonSerializer<Duration>() {
                @Override
                public JsonElement serialize(Duration src, Type typeOfSrc, JsonSerializationContext context) {
                    return new JsonPrimitive(src.toString()); // Преобразуем Duration в строку ISO
                }
            }).create();

    @BeforeEach
    public void setUp() {
        taskManager.deleteAllTasks();
        taskManager.deleteSubtasks();
        taskManager.deleteAllTasks();
        try {
            taskServer.start();
        } catch (IOException ex ) {
            System.out.println(Arrays.toString(ex.getStackTrace()));
        }

    }

    // POST и получение -------------------------------------
    @Test
    public void testAddTask() throws IOException, InterruptedException {
        // создаём задачу
        Task task = new Task("Test 2",  Progress.NEW, "Testing task 2");
        task.setDuration( Duration.ofMinutes(5));
        task.setStartTime(LocalDateTime.now());

        String taskJson = gson.toJson(task);

        HttpClient client = HttpClient.newHttpClient();
        URI url = URI.create("http://localhost:8080/tasks");
        HttpRequest request = HttpRequest.newBuilder().uri(url).POST(HttpRequest.BodyPublishers.ofString(taskJson)).build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        assertEquals(201, response.statusCode());

        List<Task> tasksFromManager = taskManager.getTasks();

        assertNotNull(tasksFromManager, "Задачи не возвращаются");
        assertEquals(1, tasksFromManager.size(), "Некорректное количество задач");
        assertEquals("Test 2", tasksFromManager.get(0).getName(), "Некорректное имя задачи");
    } // +++

    @Test
    public void testAddEpic() throws IOException, InterruptedException {
        // создаём задачу
        Epic epic = new Epic("epic1", "fdf");
        epic.setDuration( Duration.ofMinutes(5));
        epic.setStartTime(LocalDateTime.now());

        String taskJson = gson.toJson(epic);

        HttpClient client = HttpClient.newHttpClient();
        URI url = URI.create("http://localhost:8080/epics");
        HttpRequest request = HttpRequest.newBuilder().uri(url).POST(HttpRequest.BodyPublishers.ofString(taskJson)).build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        // проверяем код ответа
        assertEquals(201, response.statusCode());

        List<Epic> epicsFromTaskManger = (List<Epic>) taskManager.getEpics();

        assertNotNull(epicsFromTaskManger, "Задачи не возвращаются");
        assertEquals(1, epicsFromTaskManger.size(), "Некорректное количество задач");
        assertEquals("epic1", epicsFromTaskManger.get(0).getName(), "Некорректное имя задачи");
        taskManager.deleteAllTasks();
    } // +++

    @Test
    public void testAddSubTask() throws IOException, InterruptedException {

        Subtask subtask = new Subtask("sub1", "до", IN_PROGRESS);
        subtask.setDuration( Duration.ofMinutes(5));
        subtask.setStartTime(LocalDateTime.now());

        String taskJson = gson.toJson(subtask);


        HttpClient client = HttpClient.newHttpClient();
        URI url = URI.create("http://localhost:8080/subtasks");
        HttpRequest request = HttpRequest.newBuilder().uri(url).POST(HttpRequest.BodyPublishers.ofString(taskJson)).build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        assertEquals(201, response.statusCode());

        List<Subtask> tasksFromManager = taskManager.getAllSubs();

        assertNotNull(tasksFromManager, "Задачи не возвращаются");
        assertEquals(1, tasksFromManager.size(), "Некорректное количество задач");
        assertEquals("sub1", tasksFromManager.get(0).getName(), "Некорректное имя задачи");
    } // +++

    @Test
    public void testGetHistory() throws IOException, InterruptedException {
        // создаём задачу
        Task task = new Task("task1", Progress.NEW, "dfdf");
        task.setDuration( Duration.ofMinutes(5));
        task.setStartTime(LocalDateTime.now());
        taskManager.createTask(task);

        String taskJson = gson.toJson(task);

        HttpClient client = HttpClient.newHttpClient();
        URI url = URI.create("http://localhost:8080/history");
        HttpRequest request = HttpRequest.newBuilder().uri(url).POST(HttpRequest.BodyPublishers.ofString(taskJson)).build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        assertEquals(200, response.statusCode());

        taskManager.getTaskById(1);
        List<Task> epicsFromTaskManger = (List<Task>) taskManager.getHistory();

        assertNotNull(epicsFromTaskManger, "Задачи не возвращаются");
        assertEquals(1, epicsFromTaskManger.size(), "Некорректное количество задач");
        assertEquals("task1", epicsFromTaskManger.get(0).getName(), "Некорректное имя задачи");
    } // +++

    //--------------------------------------------------------------------------
    //по айдишникам GET  -------------------------------------------------------------

    @Test
    public void testGetTaskById() throws IOException, InterruptedException {
        // создаём задачу
        Task task = new Task("Test 2",  Progress.NEW, "Testing task 2");
        task.setDuration( Duration.ofMinutes(5));
        task.setStartTime(LocalDateTime.now());
        taskManager.createTask(task);

        String taskJson = gson.toJson(task);

        HttpClient client = HttpClient.newHttpClient();
        URI url = URI.create("http://localhost:8080/task_by_id/1");
        HttpRequest request = HttpRequest.newBuilder().uri(url).GET().build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        assertEquals(200, response.statusCode());

        Task taskFromTaskManager = taskManager.getTaskById(1);

        assertNotNull(task, "Задачи не возвращаются");
        assertEquals(taskFromTaskManager, task);
    } //+++

    @Test
    public void testGetEpicById() throws IOException, InterruptedException {

        Epic epic = new Epic("epic1", "fdf");
        epic.setDuration( Duration.ofMinutes(5));
        epic.setStartTime(LocalDateTime.now());
        taskManager.createEpic(epic);

        String taskJson = gson.toJson(epic);

        HttpClient client = HttpClient.newHttpClient();
        URI url = URI.create("http://localhost:8080/epic_by_id/1");
        HttpRequest request = HttpRequest.newBuilder().uri(url).GET().build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        assertEquals(200, response.statusCode());

        Epic taskFromTaskManager = taskManager.getEpicById(1);

        assertNotNull(epic, "Задачи не возвращаются");
        assertEquals(taskFromTaskManager, epic);
    } //+++

    @Test
    public void testGetSubTaskById() throws IOException, InterruptedException {

        Subtask subtask = new Subtask("sub1", "долг по матрице", IN_PROGRESS);
        subtask.setDuration( Duration.ofMinutes(5));
        subtask.setStartTime(LocalDateTime.now());
        taskManager.createSubTask(subtask);

        String taskJson = gson.toJson(subtask);

        HttpClient client = HttpClient.newHttpClient();
        URI url = URI.create("http://localhost:8080/subtask_by_id/1");
        HttpRequest request = HttpRequest.newBuilder().uri(url).GET().build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        assertEquals(200, response.statusCode());

        Subtask subTaskFromTaskManager = taskManager.getSubtaskById(1);

        assertNotNull(subtask, "Задачи не возвращаются");
        assertEquals(subTaskFromTaskManager, subtask);
    } //+++


    // --------------------------------------------------------------------------
    // по айдишникам DELETE -----------------------------------------------------------------

    @Test
    public void testDeleteTaskById() throws IOException, InterruptedException {

        Task task = new Task("Test 2",  Progress.NEW, "Testing task 2");
        task.setDuration( Duration.ofMinutes(5));
        task.setStartTime(LocalDateTime.now());
        taskManager.createTask(task);

        String taskJson = gson.toJson(task);

        HttpClient client = HttpClient.newHttpClient();
        URI url = URI.create("http://localhost:8080/task_by_id/1");
        HttpRequest request = HttpRequest.newBuilder().uri(url).DELETE().build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        assertEquals(200, response.statusCode());

        List<Subtask> taskFromTaskManager = taskManager.getAllSubs();

        assertNotNull(task, "Задачи не возвращаются");
        assertEquals(taskFromTaskManager.size(), 0);
    } //+++

    @Test
    public void testDeleteEpicById() throws IOException, InterruptedException {

        Epic epic = new Epic("epic1", "fdf");
        epic.setDuration( Duration.ofMinutes(5));
        epic.setStartTime(LocalDateTime.now());
        taskManager.createEpic(epic);

        String taskJson = gson.toJson(epic);

        HttpClient client = HttpClient.newHttpClient();
        URI url = URI.create("http://localhost:8080/epic_by_id/1");
        HttpRequest request = HttpRequest.newBuilder().uri(url).DELETE().build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        assertEquals(200, response.statusCode());

        List<Epic> taskFromTaskManager = (List<Epic>) taskManager.getEpics();

        assertNotNull(epic, "Задачи не возвращаются");
        assertEquals(taskFromTaskManager.size(), 0);
    } //+++

    @Test
    public void testDeleteSubTaskById() throws IOException, InterruptedException  {

        Subtask subtask = new Subtask("sub1", "долг по матрице", IN_PROGRESS);
        Epic epic = new Epic("epic1", "fdf");
        taskManager.createEpic(epic);
        subtask.setDuration( Duration.ofMinutes(5));
        subtask.setStartTime(LocalDateTime.now());
        subtask.setEpicId(1);
        taskManager.addSubtask(subtask);


        HttpClient client = HttpClient.newHttpClient();
        URI url = URI.create("http://localhost:8080/subtask_by_id/2");
        HttpRequest request = HttpRequest.newBuilder().uri(url).DELETE().build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        assertEquals(200, response.statusCode());

        List<Subtask> taskFromTaskManager = taskManager.getAllSubs();

        assertNotNull(subtask, "Задачи не возвращаются");
        assertEquals(taskFromTaskManager.size(), 0);
    } // + -

    // -------------------------------------------------------------------------------
    // по сабтаски эпика по айди

    @Test
    public void testGetEpicSubTasks() throws IOException, InterruptedException {

        Epic epic = new Epic("epic1", "fdf");
        Subtask subtask1 = new Subtask("sub1", "дpijm", IN_PROGRESS);
        Subtask subtask2 = new Subtask("sub2", "дpijm", IN_PROGRESS);
        subtask1.setEpicId(1);
        subtask2.setEpicId(1);
        epic.setDuration( Duration.ofMinutes(5));
        epic.setStartTime(LocalDateTime.now());
        taskManager.createEpic(epic);
        taskManager.createSubTask(subtask1);
        taskManager.addSubtask(subtask1);
        taskManager.createSubTask(subtask2);
        taskManager.addSubtask(subtask2);

        HttpClient client = HttpClient.newHttpClient();
        URI url = URI.create("http://localhost:8080/epic_subtasks/1");
        HttpRequest request = HttpRequest.newBuilder().uri(url).GET().build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        assertEquals(200, response.statusCode());

        List<Subtask> taskFromTaskManager = (List<Subtask>) taskManager.getAllEpicSubTasks(1);

        assertNotNull(epic, "Задачи не возвращаются");
        assertEquals(taskFromTaskManager.size(), 2);
    } //+++
}