package task;

import manager.HistoryManager;
import manager.ITaskManager;
import manager.InMemoryHistoryManager;
import manager.InMemoryTaskManager;

public class Managers {

    public static ITaskManager getDefault() {
        return new InMemoryTaskManager();
    }

    public static HistoryManager getDefaultHistory() {
        return new InMemoryHistoryManager();
    }
}