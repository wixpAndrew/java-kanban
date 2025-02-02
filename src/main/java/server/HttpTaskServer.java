package server;

import com.sun.net.httpserver.HttpServer;
import manager.ITaskManager;
import server.Epics.EpicByIdHandler;
import server.Epics.GetEpicsHandler;
import server.Subtasks.SubTaskById;
import server.Tasks.*;
import server.Epics.GetEpicsHandler;
import server.Tasks.GetPrioritizedHandler;
import server.Subtasks.GetSubTaskHandler;
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

        Epic epic1 = new Epic("Первый эпик",  "");

        epic1.setId(1);

        epic1.addSubTask(subtask1);
        epic1.addSubTask(subtask2);

        taskManager.addTask(task2);
        taskManager.addTask(task1);

        taskManager.addEpic(epic1);

        taskManager.addSubtask(subtask1);
        taskManager.addSubtask(subtask2);

        taskManager.getTaskById(1);


        httpServer.createContext("/hello", new HelloHandler());//+++

        httpServer.createContext("/tasks", new GetTasksHandler(taskManager));//+++
        httpServer.createContext("/subtasks", new GetSubTaskHandler(taskManager));//+++
        httpServer.createContext("/epics", new GetEpicsHandler(taskManager));//+++
        //classic
        httpServer.createContext("/task_by_id", new TaskByIdHandler(taskManager));//+++
        httpServer.createContext("/epic_by_id", new EpicByIdHandler(taskManager));//+++
        httpServer.createContext("/subtask_by_id", new SubTaskById(taskManager));// +/-
        // with id
        httpServer.createContext("/prioritized", new GetPrioritizedHandler(taskManager));
        httpServer.createContext("/history", new GetPrioritizedHandler(taskManager));
        //other
        httpServer.start();

        System.out.println("HTTP-сервер запущен на " + PORT + " порту!");

    }
}

