package controllers;

import model.Task;

import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.List;
import java.util.Queue;

public class InMemoryHistoryManager implements HistoryManager{
    private Queue<Task> history = new ArrayDeque<>(10);
    @Override
    public void add(Task task) {
        history.add(task);
    }
    @Override
    public List<Task> getHistory() {
        return history.stream().toList();
    }
}
