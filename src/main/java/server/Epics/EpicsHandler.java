package server.Epics;

import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import manager.ITaskManager;
import server.BaseHttpHandler;
import server.UtilHelper;
import task.Epic;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.List;

public class EpicsHandler implements HttpHandler {
    private final ITaskManager taskManager;
    private final  Gson gson = UtilHelper.getGsonBuilder();

    public EpicsHandler(ITaskManager taskManager) {
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
                String response = gson.toJson(epics);
                BaseHttpHandler.sendText(httpExchange, response);
                break;
            case "POST" :
                StringBuilder sb = new StringBuilder(body);
                String json = sb.toString();
                Epic epic = gson.fromJson(json, Epic.class);
                taskManager.createEpic(epic);
                try (OutputStream os = httpExchange.getResponseBody()) {
                    httpExchange.sendResponseHeaders(201,0);  ;
                }
                break;
        }
    }
}