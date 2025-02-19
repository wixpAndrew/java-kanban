package server.Epics;

import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import manager.ITaskManager;
import server.UtilHelper;
import task.Epic;
import task.Subtask;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.util.List;

public class EpicsSubTaskHandler implements HttpHandler {

    private final ITaskManager taskManager;
    Gson gson = UtilHelper.getGsonBuilder();

    public EpicsSubTaskHandler(ITaskManager taskManager) {
        this.taskManager = taskManager;
    }

    @Override
    public void handle(HttpExchange httpExchange) throws IOException {

        Epic epicByPath = null;

        try {
            String path = httpExchange.getRequestURI().getPath();
            String idString = path.substring(path.lastIndexOf('/') + 1);
            int id = Integer.parseInt(idString);
            epicByPath = taskManager.getEpicById(id);
        } catch (NullPointerException exception) {
            exception.getMessage();
            httpExchange.sendResponseHeaders(404, 0);
        }

        assert epicByPath != null;
        List<Subtask> result = taskManager.getAllEpicSubTasks(epicByPath.getId());

        String response = gson.toJson(result);

        httpExchange.sendResponseHeaders(200, response.getBytes(StandardCharsets.UTF_8).length);
        try (OutputStream os = httpExchange.getResponseBody()) {
            assert result != null;
            os.write(response.getBytes());
        }
    }
}
