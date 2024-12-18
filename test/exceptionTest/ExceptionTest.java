package exceptionTest;

import manager.FileBackedTaskManager;
import manager.InMemoryTaskManager;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class ExceptionTest {

    @Test
    public void fileSavingTest() throws IOException {
        Path tempFile = Files.createTempFile(null, null);
        FileBackedTaskManager fileBackedTaskManager = new FileBackedTaskManager(tempFile.toFile());
        InMemoryTaskManager inMemoryTaskManager = new InMemoryTaskManager();



    }
}
