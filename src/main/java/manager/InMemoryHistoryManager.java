package manager;

import history.HistoryList;
import task.Task;

import java.util.List;

public class InMemoryHistoryManager implements HistoryManager {
    public HistoryList historyList = new HistoryList();

    @Override
    public void add(Task task) {
        historyList.add(task);
    }

    @Override
    public List<Task> getHistory() {
        return historyList.getHistory();
    }

    @Override
    public void remove(int id) {
        historyList.remove(id);
    }
}
