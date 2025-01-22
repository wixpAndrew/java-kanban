package server;

import com.sun.net.httpserver.HttpServer;
import manager.ITaskManager;
import task.Managers;
import task.Progress;
import task.Task;

import java.io.IOException;
import java.net.InetSocketAddress;

public class HttpTaskServer {

    private static final int PORT = 8080;

    public static void main(String[] args) throws IOException {
        HttpServer httpServer = HttpServer.create(new InetSocketAddress(PORT), 0);

        ITaskManager taskManager = Managers.getDefault();

        Task task1 = new Task("name1", Progress.DONE, "dfdf");
        Task task2 = new Task("name2", Progress.NEW, "dfdf");

        taskManager.addTask(task2);
        taskManager.addTask(task1);

        httpServer.createContext("/hello", new HelloHandler());
        httpServer.createContext("/tasks", new GetTasksHandler(taskManager));
        httpServer.createContext("/task/id", new GetTaskByIdHandler());
        httpServer.start();
        System.out.println("HTTP-сервер запущен на " + PORT + " порту!");

    }
}

