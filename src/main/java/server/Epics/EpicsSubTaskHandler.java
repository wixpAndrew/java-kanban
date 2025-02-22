package server.Epics;

import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import manager.ITaskManager;
import server.BaseHttpHandler;
import server.UtilHelper;
import task.Epic;
import task.Subtask;

import java.io.IOException;
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
            BaseHttpHandler.sendNotFound(httpExchange, "");
        }

        assert epicByPath != null;
        List<Subtask> result = taskManager.getAllEpicSubTasks(epicByPath.getId());

        String response = gson.toJson(result);
        BaseHttpHandler.sendText(httpExchange, response);
    }
}
