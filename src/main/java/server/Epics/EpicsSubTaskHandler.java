package server.Epics;

import com.google.gson.*;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import manager.ITaskManager;
import task.Epic;
import task.Subtask;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class EpicsSubTaskHandler implements HttpHandler {

    private ITaskManager taskManager;
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSSSS")

    public EpicsSubTaskHandler (ITaskManager taskManager) {
        this.taskManager = taskManager;
    }

    @Override
    public void handle(HttpExchange httpExchange) throws IOException {

        InputStream inputStream = httpExchange.getRequestBody();
        String body = new String(inputStream.readAllBytes(), StandardCharsets.UTF_8);
        String exchangeMethod = httpExchange.getRequestMethod();

        Gson gson_builder = new GsonBuilder()
                .excludeFieldsWithoutExposeAnnotation()
                .registerTypeAdapter(LocalDateTime.class, new JsonDeserializer<LocalDateTime>() {
                    @Override
                    public LocalDateTime deserialize(JsonElement json, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
                        return LocalDateTime.parse(json.getAsJsonPrimitive().getAsString(), formatter);
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

        Epic epic_by_path = null;

        try {
            String idString = extractIdFromPath(httpExchange.getRequestURI().toString());
            assert idString != null;
            int id = Integer.parseInt(idString);
            epic_by_path = taskManager.getEpicById(id);
        } catch (NullPointerException exception) {
            exception.getMessage();
            httpExchange.sendResponseHeaders(404, 0);
        }

        assert epic_by_path != null;
        List<Subtask> result = taskManager.getAllEpicSubTasks(epic_by_path.getId());

        String response = gson_builder.toJson(result);

        httpExchange.sendResponseHeaders(200, response.getBytes(StandardCharsets.UTF_8).length);
        try (OutputStream os = httpExchange.getResponseBody()) {
            assert result != null;
            os.write(response.getBytes());
        }
    }

    public static String extractIdFromPath(String path) {
        // Находим индекс начала и конца id в строке
        int startIndex = path.indexOf("epics/") + 6;
        int endIndex = path.indexOf("/subtasks");

        if (startIndex != -1 && endIndex != -1) {
            return path.substring(startIndex, endIndex);
        }

        return null;
    }
}
