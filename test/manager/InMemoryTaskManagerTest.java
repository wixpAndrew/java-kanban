package manager;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import task.Progress;
import task.Task;

import java.time.Duration;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

public class InMemoryTaskManagerTest extends TaskManagerTest<InMemoryTaskManager> {

    @BeforeEach
    public void init() {
        this.manager = new InMemoryTaskManager();
    }

    @Test
    void testIsItRightWorkingTasks() {
        // Создаём задачи с различными интервалами
        Task task1 = new Task("Task 1", Progress.DONE ,"Description");
        Task task2 = new Task( "Task 2",  Progress.DONE, "dfdf");
        Task task3 = new Task("Task 3", Progress.DONE, "DFDF");
        task1.setStartTime(LocalDateTime.of(2024, 12, 19, 10, 0));
        task1.setEndTime(LocalDateTime.of(2024, 12, 19, 12, 0));
        task2.setStartTime(LocalDateTime.of(2024, 12, 19, 14, 0));
        task2.setEndTime(LocalDateTime.of(2024, 12, 19, 15, 0));
        task3.setStartTime(LocalDateTime.of(2024, 12, 19, 18, 0));
        task3.setEndTime(LocalDateTime.of(2024, 12, 19, 19, 0));
        this.manager.addTask(task1);
        this.manager.prioritTasks.add(task1);
        this.manager.addTask(task2);
        this.manager.prioritTasks.add(task2);
        this.manager.addTask(task3);
        this.manager.prioritTasks.add(task3);
        task1.setDuration(Duration.between(task1.getStartTime(), task1.getEndTime()));
        task2.setDuration(Duration.between(task2.getStartTime(), task2.getEndTime()));
        task3.setDuration(Duration.between(task3.getStartTime(), task3.getEndTime()));
        // Проверка пересечения
        assertEquals(this.manager.isItRightWorkingTasks(), false);
    }
}