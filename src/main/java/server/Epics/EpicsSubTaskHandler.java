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
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.List;

public class EpicsSubTaskHandler implements HttpHandler {

    private ITaskManager taskManager;

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
                        return ZonedDateTime.parse(json.getAsJsonPrimitive().getAsString()).toLocalDateTime();
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

        try (OutputStream os = httpExchange.getResponseBody()) {
            assert result != null;
            os.write(response.getBytes());
            httpExchange.sendResponseHeaders(200, response.getBytes(StandardCharsets.UTF_8).length);
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
