package server.Epics;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import manager.ITaskManager;
import server.UtilHelper;
import task.Epic;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class GetEpicsHandler implements HttpHandler {
    private final ITaskManager taskManager;
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSSSS");

    public GetEpicsHandler(ITaskManager taskManager) {
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
                List<Epic> epics = (List<Epic>) taskManager.getEpics();
                String response = UtilHelper.getGsonBuilder(formatter).toJson(epics);

                try (OutputStream os = httpExchange.getResponseBody()) {
                    httpExchange.sendResponseHeaders(200, response.getBytes(StandardCharsets.UTF_8).length);
                    os.write(response.getBytes(StandardCharsets.UTF_8));
                }
                break;
            case "POST" :
                StringBuilder sb = new StringBuilder(body);
                String respon = "круто";
                String json = sb.toString();
                Epic epic = UtilHelper.getGsonBuilder(formatter).fromJson(json, Epic.class);
                taskManager.createEpic(epic);

                try (OutputStream os = httpExchange.getResponseBody()) {
                    httpExchange.sendResponseHeaders(201, respon.getBytes(StandardCharsets.UTF_8).length);
                    os.write(respon.getBytes(StandardCharsets.UTF_8));
                }
                break;
        }
    }
}