package task;
import manager.*;

public class Managers {

    public static ITaskManager getDefault() {
        return new InMemoryTaskManager();
    }

    public static HistoryManager getDefaultHistory()    {
        return new InMemoryHistoryManager();
    }
}