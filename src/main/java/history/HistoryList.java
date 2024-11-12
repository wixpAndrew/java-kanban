package history;

import task.Task;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HistoryList {

    private HistoryNode<Task> head;
    private HistoryNode<Task> tail;
    private final Map<Integer, HistoryNode<Task>> tasksMap;

    public HistoryList() {
        this.head = null;
        this.tasksMap = new HashMap<>();
    }

    public void add(Task task) {
        // пустой список - даже нет головы
        if (head == null) {
            // ни одного элемента еще не было добавлено
            head = new HistoryNode<>(task, null, null);
            tail = head;
            tasksMap.put(task.getId(), head);
        } else {
            tail.next = new HistoryNode<>(task, null, tail);
            tail = tail.next;
            tasksMap.put(task.getId(), tail);
        }
    }

    public void remove(int taskId) {
        var task = tasksMap.get(taskId);
        if (task != null) {
            tasksMap.remove(taskId);
            if (task == head) {
                head = head.next;
            } else if (task == tail) {
                tail = tail.prev;
            } else {
                task.prev.next = task.next;
                task.next.prev = task.prev;
                tasksMap.remove(taskId);
            }
        }
    }

    public List<Task> getHistory() {
        List<Task> result = new ArrayList<>();

        if (head != null) {
            result.add(head.value);
            var node = head.next;
            while (node != null) {
                result.add(node.value);
                node = node.next;
            }
        }

        return result;
    }

    private static class HistoryNode<V> {
        private final V value;
        private HistoryNode<V> next;
        private HistoryNode<V> prev;

        private HistoryNode(V value, HistoryNode<V> next, HistoryNode<V> prev) {
            this.value = value;
            this.next = next;
            this.prev = prev;
        }
    }
}
