package server.Subtasks;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import manager.ITaskManager;
import task.Subtask;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;

public class SubTaskById implements HttpHandler {

    private ITaskManager taskManager;

    public SubTaskById(ITaskManager taskManager) {
        this.taskManager = taskManager;
    }

    @Override
    public void handle(HttpExchange httpExchange) throws IOException {

        InputStream inputStream = httpExchange.getRequestBody();
        Subtask result  = null;
        String name = new String(inputStream.readAllBytes(), StandardCharsets.UTF_8);
        System.out.println("Тело запроса:\n" + name);
        String exchangeMethod = httpExchange.getRequestMethod();

        switch (exchangeMethod) {
            case "GET" :
                try {
                    result = taskManager.getSubtaskById(Integer.parseInt(extractAfterSlash(httpExchange.getRequestURI().toString())));
                } catch (NullPointerException exception) {
                    httpExchange.sendResponseHeaders(404, 0);
                }
                httpExchange.sendResponseHeaders(200, 0);
                try (OutputStream os = httpExchange.getResponseBody()) {
                    assert result != null;
                    String response = result.subTasktoString();
                    os.write(response.getBytes());
                }
            case "DELETE" :
                try {
                    taskManager.deleteSub(Integer.parseInt(extractAfterSlash(httpExchange.getRequestURI().toString())));
                } catch (NullPointerException exception) {
                    httpExchange.sendResponseHeaders(404, 0);
                }
                httpExchange.sendResponseHeaders(200, 0);
            }
        }

    public static String extractAfterSlash(String url) {
        int slashIndex = url.indexOf("/");

        if (slashIndex != -1 && slashIndex < url.length() - 1) {
            return url.substring(slashIndex + 15); // <-- НЕ МЕНЯТЬ НАЗВАНИЕ КОНТЕКСТА
        } else {
            return "";
        }
    }
}

