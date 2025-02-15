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
import java.time.format.DateTimeFormatter;

public class EpicByIdHandler implements HttpHandler {

    private final ITaskManager taskManager;
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSSSS");
    Gson gson = UtilHelper.getGsonBuilder(formatter);

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
                    result = taskManager.getEpicById(Integer.parseInt(extractAfterSlash(httpExchange.getRequestURI().toString())));
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
                    taskManager.deleteEpic(Integer.parseInt(extractAfterSlash(httpExchange.getRequestURI().toString())));
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