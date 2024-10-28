package controllers;

import model.Task;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class HistoryList {

    private class HistoryNode<V> {
        public V value;
        public HistoryNode<V> next;
        public HistoryNode<V> prev;

        HistoryNode(V value, HistoryNode<V> next, HistoryNode<V> prev) {
            this.value = value;
            this.next = next;
            this.prev = prev;
        }
    }

    private HistoryNode<Task> head;
    private HistoryNode<Task> tail;
    private HashMap<Integer, HistoryNode<Task>> tasksMap;

    HistoryList() {
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
            tail.next = new HistoryNode<>(task,null, tail);
            tail = tail.next;
            tasksMap.put(task.getId(), tail);
        }
    }

    public void remove(int taskId) {
        var task = tasksMap.get(taskId);
        if (task != null) {
            tasksMap.remove(task);
            // значит она у нас хранится
            // кейс 1 - мы = голова
            if (task == head) {
                head = head.next;
            }
            // кейс 2 - мы = где то между хвостом и голово
            // кейс 3 - мы = хвост
            else if (task == tail) {
                tail = tail.prev;
            }
            else {
                task.prev.next = task.next;
                task.next.prev = task.prev;
                tasksMap.remove(taskId);
            }
        }
    }

    public List<Task> getHistory() {
        ArrayList<Task> result = new ArrayList<>();

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
}
