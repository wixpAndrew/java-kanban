package server.Epics;

import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import manager.ITaskManager;
import server.UtilHelper;
import task.Epic;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;

public class EpicByIdHandler implements HttpHandler {

    private final ITaskManager taskManager;
    Gson gson = UtilHelper.getGsonBuilder();

    public EpicByIdHandler(ITaskManager taskManager) {
        this.taskManager = taskManager;
    }

    @Override
    public void handle(HttpExchange httpExchange) throws IOException {
        InputStream inputStream = httpExchange.getRequestBody();
        Epic result  = null;
        String exchangeMethod = httpExchange.getRequestMethod();
        String name = new String(inputStream.readAllBytes(), StandardCharsets.UTF_8);
        System.out.println("Тело запроса:\n" + name);
        switch (exchangeMethod) {
            case "GET" :
                try {
                    String path = httpExchange.getRequestURI().getPath();
                    String idString = path.substring(path.lastIndexOf('/') + 1);
                    result = taskManager.getEpicById(Integer.parseInt(idString));
                } catch (NullPointerException exception) {
                    httpExchange.sendResponseHeaders(404, 0);
                }
                String response = gson.toJson(result);
                httpExchange.sendResponseHeaders(200, response.getBytes(StandardCharsets.UTF_8).length);
                try (OutputStream os = httpExchange.getResponseBody()) {
                    assert result != null;
                    os.write(response.getBytes());
                }
                break;
            case "DELETE" :
                try {
                    String path = httpExchange.getRequestURI().getPath();
                    String idString = path.substring(path.lastIndexOf('/') + 1);
                    taskManager.deleteEpic(Integer.parseInt(idString));
                    httpExchange.sendResponseHeaders(200, 0);
                } catch (NullPointerException exception) {
                    httpExchange.sendResponseHeaders(404, 0);
                }
                httpExchange.getResponseBody().close();
                break;
        }
    }
}