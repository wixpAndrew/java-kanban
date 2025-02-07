package server.Prioritized;

import com.google.gson.*;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import manager.ITaskManager;
import task.Task;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.List;

public class GetPrioritizedHandler implements HttpHandler {
    private ITaskManager taskManager;

    public GetPrioritizedHandler(ITaskManager taskManager) {
        this.taskManager = taskManager;
    }

    @Override
    public void handle(HttpExchange httpExchange) throws IOException {

        InputStream inputStream = httpExchange.getRequestBody();

        String name = new String(inputStream.readAllBytes(), StandardCharsets.UTF_8);
        String exchangeMethod = httpExchange.getRequestMethod();
                System.out.println("Тело запроса:\n" + name);
                List<Task> tasks = taskManager.getPrioritizedTasks();
                Gson gson = new GsonBuilder()
                        .excludeFieldsWithoutExposeAnnotation()
                        .registerTypeAdapter(LocalDateTime.class, new JsonDeserializer<LocalDateTime>() {
                            @Override
                            public LocalDateTime deserialize(JsonElement json, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
                                return ZonedDateTime.parse(json.getAsJsonPrimitive().getAsString()).toLocalDateTime();
                            }
                        }).create();
                String response = gson.toJson(tasks);
                try (OutputStream os = httpExchange.getResponseBody()) {
                    httpExchange.sendResponseHeaders(200, response.getBytes(StandardCharsets.UTF_8).length);
                    os.write(response.getBytes(StandardCharsets.UTF_8));
                }
        }
}