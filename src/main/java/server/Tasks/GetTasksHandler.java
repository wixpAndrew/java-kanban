package server.Tasks;

import com.google.gson.*;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import manager.ITaskManager;
import task.Task;

import java.io.*;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class GetTasksHandler implements HttpHandler {
    private ITaskManager taskManager;
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSSSS");

    public GetTasksHandler(ITaskManager taskManager) {
        this.taskManager = taskManager;
    }

    @Override
    public void handle(HttpExchange httpExchange) throws IOException {

        InputStream inputStream = httpExchange.getRequestBody();
        String body = new String(inputStream.readAllBytes(), StandardCharsets.UTF_8);

        String exchangeMethod = httpExchange.getRequestMethod();
        System.out.println("Тело запроса:\n" + body);

        Gson gsonBuilder = new GsonBuilder()
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


        switch (exchangeMethod) {
            case "GET" :
                List<Task> tasks = taskManager.getTasks();
                String response = gsonBuilder.toJson(tasks);

                try (OutputStream os = httpExchange.getResponseBody()) {
                    httpExchange.sendResponseHeaders(200, response.getBytes(StandardCharsets.UTF_8).length);
                    os.write(response.getBytes(StandardCharsets.UTF_8));
                }
                break;

            case "POST" :
                StringBuilder sb = new StringBuilder(body);
                String json = sb.toString();
                Task task = gsonBuilder.fromJson(json, Task.class);

                if (task.getId() == null) {
                    taskManager.createTask(task);
                } else {
                    taskManager.updateTask(task);
                }

                try (OutputStream os = httpExchange.getResponseBody()) {
                    String respons = "круто";
                    httpExchange.sendResponseHeaders(201, respons.getBytes(StandardCharsets.UTF_8).length);
                    os.write(respons.getBytes(StandardCharsets.UTF_8));
                }
                break;
        }
    }
}