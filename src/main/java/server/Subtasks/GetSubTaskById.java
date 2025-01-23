package server.Subtasks;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import manager.ITaskManager;
import task.Managers;
import task.Subtask;
import task.Task;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;

public class GetSubTaskById implements HttpHandler {

    private ITaskManager taskManager;

    public GetSubTaskById (ITaskManager taskManager) {
        this.taskManager = taskManager;
    }

    @Override
    public void handle(HttpExchange httpExchange) throws IOException {

        InputStream inputStream = httpExchange.getRequestBody();
        Subtask result  = null;

        try {
            result = taskManager.getSubtaskById(Integer.parseInt(extractAfterSlash(httpExchange.getRequestURI().toString())));
        } catch (NullPointerException exception) {
            httpExchange.sendResponseHeaders(404, 0);
        }

        String name = new String(inputStream.readAllBytes(), StandardCharsets.UTF_8);
        System.out.println("Тело запроса:\n" + name);

        httpExchange.sendResponseHeaders(200, 0);

        try (OutputStream os = httpExchange.getResponseBody()) {
            assert result != null;
            String response = result.tasktoString();
            os.write(response.getBytes());
        }
    }

    public static String extractAfterSlash(String url) {
        int slashIndex = url.indexOf("/");

        if (slashIndex != -1 && slashIndex < url.length() - 1) {
            return url.substring(slashIndex + 6);
        } else {
            return "";
        }
    }
}