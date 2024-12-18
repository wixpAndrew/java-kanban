package manager;

import org.junit.jupiter.api.Test;
import task.Progress;
import task.Task;

import static org.junit.jupiter.api.Assertions.assertEquals;

abstract class TaskManagerTest<T extends ITaskManager> {

    protected T manager;

    @Test
    public void testAddTask() {
        manager.addTask(new Task("sfdfdf", Progress.NEW, "dfd"));
    }

    @Test
    public void testGetTasks() {

    }

    @Test
    public void testGetTaskById() {

    }

    @Test
    public void testDeleteAllTasks() {

    }

    @Test
    public void testDeleteTask() {

    }

    @Test
    public void testUpdateTask() {

    }

    @Test
    public void testGetTaskMap() {

    }

    @Test
    public void testAddEpic() {

    }

    @Test
    public void testGetAllEpics() {

    }

}
