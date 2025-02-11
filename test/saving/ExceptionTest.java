package saving;

import manager.FileBackedTaskManager;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class ExceptionTest {

    @Test
    public void checkingException() {
        assertThrows(IOException.class, () -> {
            FileBackedTaskManager.loadFromFile(new File(String.valueOf("tetsttest")));
        }, "Ошибка(");
    }
}
