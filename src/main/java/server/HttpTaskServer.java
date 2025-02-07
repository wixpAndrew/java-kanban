package server;

import com.sun.net.httpserver.HttpServer;
import manager.ITaskManager;
import server.Epics.EpicByIdHandler;
import server.Epics.EpicsSubTaskHandler;
import server.Epics.GetEpicsHandler;
import server.History.GetHistoryHandler;
import server.Prioritized.GetPrioritizedHandler;
import server.Subtasks.SubTaskById;
import server.Tasks.*;
import server.Subtasks.GetSubTaskHandler;
import task.*;

import java.io.IOException;
import java.net.InetSocketAddress;

public class HttpTaskServer {

    private static final int PORT = 8080;
    ITaskManager taskManager;

    public HttpTaskServer(ITaskManager taskManager) {
        this.taskManager = taskManager;
    }

    public void start() throws IOException {
        HttpServer httpServer = HttpServer.create(new InetSocketAddress(PORT), 0);


        httpServer.createContext("/hello", new HelloHandler());//+++

        httpServer.createContext("/tasks", new GetTasksHandler(taskManager));//+++
        httpServer.createContext("/subtasks", new GetSubTaskHandler(taskManager));//+++
        httpServer.createContext("/epics", new GetEpicsHandler(taskManager));//+++
        //classic
        httpServer.createContext("/task_by_id", new TaskByIdHandler(taskManager));//+++
        httpServer.createContext("/epic_by_id", new EpicByIdHandler(taskManager));//+++
        httpServer.createContext("/subtask_by_id", new SubTaskById(taskManager));// +
        httpServer.createContext("/epic_subtasks", new EpicsSubTaskHandler(taskManager));
        // with id
        httpServer.createContext("/prioritized", new GetPrioritizedHandler(taskManager));
        httpServer.createContext("/history", new GetHistoryHandler(taskManager));
        //other
        httpServer.start();

        System.out.println("HTTP-сервер запущен на " + PORT + " порту!");
    }



}

