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
    public void testDeleteAllEpics() {

    }

    @Test
    public void testDeleteEpic() {

    }
    @Test
    public void testUpdateEpic() {

    }
    @Test
    public void testAddSubtask() {

    }
    @Test
    public void testGetAllEpicSubTasks() {

    }
    @Test
    public void testDeleteSubtasks() {

    }
    @Test
    public void testUpdateSubTask() {

    }
    @Test
    public void testGetSubtaskById() {

    }
    @Test
    public void testGetAllSubs() {

    }
    @Test
    public void testGetHistory() {

    }
    @Test
    public void testGetHistoryManager() {

    }
    @Test
    public void testGetSubTasksMap() {

    }
    @Test
    public void testGetPrioritizedTasks() {

    }
    @Test
    public void testIsItRightWorkingTasks() {

    }
}
