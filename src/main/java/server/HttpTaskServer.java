package server;

import com.sun.net.httpserver.HttpServer;
import manager.ITaskManager;
import server.Epics.EpicByIdHandler;
import server.Epics.EpicsSubTaskHandler;
import server.Epics.EpicsHandler;
import server.History.GetHistoryHandler;
import server.Prioritized.GetPrioritizedHandler;
import server.Subtasks.SubTaskById;
import server.Tasks.*;
import server.Subtasks.SubTaskHandler;

import java.io.IOException;
import java.net.InetSocketAddress;

public class HttpTaskServer {

    private static final int PORT = 8080;
    private final ITaskManager taskManager;
    private HttpServer httpServer;

    public HttpTaskServer(ITaskManager taskManager) {
        this.taskManager = taskManager;
    }

    public void start() throws IOException {
        httpServer = HttpServer.create(new InetSocketAddress(PORT), 0);

        httpServer.createContext("/hello", new HelloHandler());//+++

        httpServer.createContext("/tasks", new TasksHandler(taskManager));
        httpServer.createContext("/subtasks", new SubTaskHandler(taskManager));
        httpServer.createContext("/epics", new EpicsHandler(taskManager));
        httpServer.createContext("/task_by_id", new TaskByIdHandler(taskManager));
        httpServer.createContext("/epic_by_id", new EpicByIdHandler(taskManager));
        httpServer.createContext("/subtask_by_id", new SubTaskById(taskManager));
        httpServer.createContext("/epic_subtasks", new EpicsSubTaskHandler(taskManager));
        httpServer.createContext("/prioritized", new GetPrioritizedHandler(taskManager));
        httpServer.createContext("/history", new GetHistoryHandler(taskManager));
        httpServer.start();

        System.out.println("HTTP-сервер запущен на " + PORT + " порту!");
    }

    public void stop() {
        httpServer.stop(2);
    }
}

