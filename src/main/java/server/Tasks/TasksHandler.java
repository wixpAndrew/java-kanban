package server.Tasks;

import com.google.gson.*;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import manager.ITaskManager;
import server.UtilHelper;
import task.Task;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.List;

public class TasksHandler implements HttpHandler {
    private final ITaskManager taskManager;
    Gson gson = UtilHelper.getGsonBuilder();

    public TasksHandler(ITaskManager taskManager) {
        this.taskManager = taskManager;
    }

    @Override
    public void handle(HttpExchange httpExchange) throws IOException {

        InputStream inputStream = httpExchange.getRequestBody();
        String body = new String(inputStream.readAllBytes(), StandardCharsets.UTF_8);

        String exchangeMethod = httpExchange.getRequestMethod();
        System.out.println("Тело запроса:\n" + body);

        switch (exchangeMethod) {
            case "GET" :
                List<Task> tasks = taskManager.getTasks();
                String response = gson.toJson(tasks);

                try (OutputStream os = httpExchange.getResponseBody()) {
                    httpExchange.sendResponseHeaders(200, response.getBytes(StandardCharsets.UTF_8).length);
                    os.write(response.getBytes(StandardCharsets.UTF_8));
                }
                break;

            case "POST" :
                StringBuilder sb = new StringBuilder(body);
                String json = sb.toString();
                Task task;
                try {
                     task = gson.fromJson(json, Task.class);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }

                if (task.getId() == null) {
                    taskManager.createTask(task);
                } else {
                    taskManager.updateTask(task);
                }
                try (OutputStream os = httpExchange.getResponseBody()) {
                    httpExchange.sendResponseHeaders(201,0);
                }
                break;
        }
    }
}