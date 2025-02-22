package server.Prioritized;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import manager.ITaskManager;
import server.PriorHistoryHelper;
import task.Task;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.List;

public class GetPrioritizedHandler implements HttpHandler {
    private final ITaskManager taskManager;

    public GetPrioritizedHandler(ITaskManager taskManager) {
        this.taskManager = taskManager;
    }

    @Override
    public void handle(HttpExchange httpExchange) throws IOException {

        InputStream inputStream = httpExchange.getRequestBody();

        String name = new String(inputStream.readAllBytes(), StandardCharsets.UTF_8);
        String exchangeMethod = httpExchange.getRequestMethod();
        System.out.println("Тело запроса:\n" + name);
        List<Task> tasks = taskManager.getPrioritizedTasks();
        PriorHistoryHelper.sendFinalMes(tasks, httpExchange);
    }
}