package server.Subtasks;

import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import manager.ITaskManager;
import server.BaseHttpHandler;
import server.UtilHelper;
import task.Subtask;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

public class SubTaskById  implements HttpHandler {

    private final ITaskManager taskManager;
    private final  Gson gson = UtilHelper.getGsonBuilder();

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
                    String path = httpExchange.getRequestURI().getPath();
                    String idString = path.substring(path.lastIndexOf('/') + 1);
                    result = taskManager.getSubtaskById(Integer.parseInt(idString));
                } catch (NullPointerException exception) {
                    httpExchange.sendResponseHeaders(404, 0);
                }

                String response = gson.toJson(result);
                BaseHttpHandler.sendText(httpExchange, response);
                break;
            case "DELETE" :
                try {
                    String path = httpExchange.getRequestURI().getPath();
                    String idString = path.substring(path.lastIndexOf('/') + 1);
                    taskManager.deleteSub(Integer.parseInt(idString));
                    httpExchange.sendResponseHeaders(200, 0);
                } catch (NullPointerException exception) {
                   BaseHttpHandler.sendNotFound(httpExchange, "");
                }
                httpExchange.getResponseBody().close();
                break;
            }
        }
    }

