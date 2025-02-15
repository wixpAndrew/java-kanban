package server.Subtasks;

import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import manager.ITaskManager;
import server.UtilHelper;
import task.Subtask;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class GetSubTaskHandler implements HttpHandler {
    private final ITaskManager taskManager;
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSSSS");
    Gson gson = UtilHelper.getGsonBuilder(formatter);


    public GetSubTaskHandler(ITaskManager taskManager) {
        this.taskManager = taskManager;
    }

    @Override
    public void handle(HttpExchange httpExchange) throws IOException {

        InputStream inputStream = httpExchange.getRequestBody();

        String body = new String(inputStream.readAllBytes(), StandardCharsets.UTF_8);
        String exchangeMethod = httpExchange.getRequestMethod();

        switch (exchangeMethod) {
            case "GET" :
                System.out.println("Тело запроса:\n" + body);
                List<Subtask> tasks = taskManager.getAllSubs();
                String response = gson.toJson(tasks);

                try (OutputStream os = httpExchange.getResponseBody()) {
                    httpExchange.sendResponseHeaders(200, response.getBytes(StandardCharsets.UTF_8).length);
                    os.write(response.getBytes(StandardCharsets.UTF_8));
                }
                break;

            case "POST" :
                StringBuilder sb = new StringBuilder(body);
                String json = sb.toString();
                Subtask subtask = gson.fromJson(json, Subtask.class);
                if (subtask.getId() == null) {
                    taskManager.createSubTask(subtask);
                } else {
                    taskManager.updateSubTask(subtask);
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