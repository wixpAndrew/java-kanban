package server;

import com.google.gson.*;
import com.sun.net.httpserver.HttpExchange;
import task.Task;

import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.List;

public class PriorHistoryHelper {
    
    static Gson gson = new GsonBuilder()
            .excludeFieldsWithoutExposeAnnotation()
            .registerTypeAdapter(LocalDateTime.class, new JsonDeserializer<LocalDateTime>() {
                @Override
                public LocalDateTime deserialize(JsonElement json, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
                    return ZonedDateTime.parse(json.getAsJsonPrimitive().getAsString()).toLocalDateTime();
                }
            }).create();

    public static void sendFinalMes(List<Task> tasks, HttpExchange httpExchange) {
        String response = gson.toJson(tasks);
        try (OutputStream os = httpExchange.getResponseBody()) {
            httpExchange.sendResponseHeaders(200, response.getBytes(StandardCharsets.UTF_8).length);
            os.write(response.getBytes(StandardCharsets.UTF_8));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
