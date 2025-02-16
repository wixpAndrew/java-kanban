package server.Tasks;

import com.google.gson.*;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import manager.ITaskManager;
import server.UtilHelper;
import task.Task;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class TaskByIdHandler implements HttpHandler {

    private final ITaskManager taskManager;
    Gson gson = UtilHelper.getGsonBuilder();

    public TaskByIdHandler(ITaskManager taskManager) {
        this.taskManager = taskManager;
    }

    @Override
    public void handle(HttpExchange httpExchange) throws IOException {

        InputStream inputStream = httpExchange.getRequestBody();
        Task result  = null;
        String name = new String(inputStream.readAllBytes(), StandardCharsets.UTF_8);
        System.out.println("Тело запроса:\n" + name);
        String exchangeMethod = httpExchange.getRequestMethod();

        switch (exchangeMethod) {
            case "GET":
                try {
                    result = taskManager.getTaskById(Integer.parseInt(extractAfterSlash(httpExchange.getRequestURI().toString())));
                } catch (NullPointerException exception) {
                    httpExchange.sendResponseHeaders(404, 0);
                }

                String response = gson.toJson(result);

                httpExchange.sendResponseHeaders(200, response.getBytes().length);
                try (OutputStream os = httpExchange.getResponseBody()) {
                    assert result != null;
                    os.write(response.getBytes());
                }
                break;
            case "DELETE" :
                try {
                    taskManager.deleteTask(Integer.parseInt(extractAfterSlash(httpExchange.getRequestURI().toString())));
                    httpExchange.sendResponseHeaders(200, 0);
                } catch (NullPointerException exception) {
                    httpExchange.sendResponseHeaders(404, 0);
                }
                httpExchange.getResponseBody().close();
                break;
        }
    }

    public static String extractAfterSlash(String url) {
        int slashIndex = url.indexOf("/");

        if (slashIndex != -1 && slashIndex < url.length() - 1) {
            return url.substring(slashIndex + 12); // <-- НЕ МЕНЯТЬ НАЗВАНИЕ КОНТЕКСТА
        } else {
            return "";
        }
    }
}



