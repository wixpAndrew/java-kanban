package server.Subtasks;

import com.google.gson.*;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import manager.ITaskManager;
import task.Subtask;
import task.Task;

import java.io.*;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.List;

public class GetSubTaskHandler implements HttpHandler {
    private ITaskManager taskManager;

    public GetSubTaskHandler (ITaskManager taskManager) {
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

        switch (exchangeMethod) {
            case "GET" :
                System.out.println("Тело запроса:\n" + body);
                List<Subtask> tasks = taskManager.getAllSubs();
                String response = gson_builder.toJson(tasks);

                try (OutputStream os = httpExchange.getResponseBody()) {
                    httpExchange.sendResponseHeaders(200, response.getBytes(StandardCharsets.UTF_8).length);
                    os.write(response.getBytes(StandardCharsets.UTF_8));
                }
                break;

            case "POST" :
                StringBuilder sb = new StringBuilder(body);
                String json = sb.toString();

                Subtask subtask = gson_builder.fromJson(json, Subtask.class);
                if (subtask.getId() == null) {
                    taskManager.createSubTask(subtask);
                } else {
                    taskManager.updateSubTask(subtask);
                }
                try (OutputStream os = httpExchange.getResponseBody()) {
                    String respons = "заебись";
                    httpExchange.sendResponseHeaders(201, respons.getBytes(StandardCharsets.UTF_8).length);
                    os.write(respons.getBytes(StandardCharsets.UTF_8));
                }
                break;
        }
    }
}