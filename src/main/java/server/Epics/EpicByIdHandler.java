package server.Epics;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import manager.ITaskManager;
import task.Epic;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;

public class EpicByIdHandler implements HttpHandler {

    private ITaskManager taskManager;

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

                httpExchange.sendResponseHeaders(200, 0);

                try (OutputStream os = httpExchange.getResponseBody()) {
                    assert result != null;
                    String response = result.epictoString();
                    os.write(response.getBytes());
                }
            case "DELETE" :
                try {
                     taskManager.deleteEpic(Integer.parseInt(extractAfterSlash(httpExchange.getRequestURI().toString())));
                } catch (NullPointerException exception) {
                    httpExchange.sendResponseHeaders(404, 0);
                }
                httpExchange.sendResponseHeaders(200, 0);
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