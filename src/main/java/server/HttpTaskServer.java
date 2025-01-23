package server;

import com.sun.net.httpserver.HttpServer;
import manager.ITaskManager;
import server.Subtasks.GetSubTaskById;
import server.Tasks.GetEpicsHandler;
import server.Tasks.GetSubTaskHandler;
import server.Tasks.GetTaskByIdHandler;
import server.Tasks.GetTasksHandler;
import task.*;

import java.io.IOException;
import java.net.InetSocketAddress;

public class HttpTaskServer {

    private static final int PORT = 8080;

    public static void main(String[] args) throws IOException {
        HttpServer httpServer = HttpServer.create(new InetSocketAddress(PORT), 0);

        ITaskManager taskManager = Managers.getDefault();

        Task task1 = new Task("name1", Progress.DONE, "dfdf");
        Task task2 = new Task("name2", Progress.NEW, "dfdf");

        Subtask subtask1 = new Subtask("Найти деньги", "", Progress.IN_PROGRESS);
        Subtask subtask2 = new Subtask("Найти людей", "", Progress.IN_PROGRESS);
        Subtask subtask3 = new Subtask("Найти что то ", "", Progress.DONE);
        subtask1.setEpicId(1);
        subtask2.setEpicId(2);

        Epic epic2 = new Epic("Второй эпик",  "");

        epic2.setId(1);

        epic2.addSubTask(subtask1);
        epic2.addSubTask(subtask2);

        taskManager.addTask(task2);
        taskManager.addTask(task1);

        taskManager.addEpic(epic2);

        taskManager.addSubtask(subtask1);
        taskManager.addSubtask(subtask2);


        httpServer.createContext("/hello", new HelloHandler());//+++
        httpServer.createContext("/tasks", new GetTasksHandler(taskManager));//+++
        httpServer.createContext("/subtasks", new GetSubTaskHandler(taskManager));//+++
        httpServer.createContext("/epics", new GetEpicsHandler(taskManager));//+++
        httpServer.createContext("/task_by_id", new GetTaskByIdHandler(taskManager));//+++
        httpServer.start();

        System.out.println("HTTP-сервер запущен на " + PORT + " порту!");

    }
}

