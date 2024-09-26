package controllers;

import model.Task;

public class Managers {
    private static HistoryManager historyManager = null;
    public static ITaskManager getDefault(){
        return new InMemoryTaskManager();
    }
    public static HistoryManager getDefaultHistory(){
        if (historyManager== null) {
            historyManager = new InMemoryHistoryManager(); // синглетон
        }
        return historyManager;
    }
}