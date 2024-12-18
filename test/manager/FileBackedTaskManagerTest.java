package manager;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class FileBackedTaskManagerTest extends TaskManagerTest<FileBackedTaskManager> {

    @BeforeEach
    public void init() throws IOException {
        Path tempFile = Files.createTempFile(null, null);
        this.manager = new FileBackedTaskManager(tempFile.toFile());
    }

    @Test
    public void testSave() {

    }
}
