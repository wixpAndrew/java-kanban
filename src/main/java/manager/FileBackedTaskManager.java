package manager;

import task.*;
import java.util.*;
import java.io.File;
import java.util.Collection;
import java.util.List;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;

public class FileBackedTaskManager extends InMemoryTaskManager  {
    File file;
    ITaskManager inMemoryTaskManager = Managers.getDefault();



    public FileBackedTaskManager (File file) throws IOException {
        this.file = file;
    }


    public void save() throws IOException {
        new FileWriter(file, false).close();
        Writer fileWriter = new FileWriter(file);
        fileWriter.write("id,type,name,status,description,epic\n");
        int count = 1;
        try {
            for (Task task : super.getTasks()) {
                fileWriter.write(count + ", " + task.TasktoString());
                count++;
                fileWriter.write("\n");
            }
            for (Epic epic : super.getAllEpics()) {
                fileWriter.write( count + ", " +  epic.EpictoString());
                count++;
                fileWriter.write("\n");
            }
            for (Subtask subtask : super.getAllSubs()) {
                fileWriter.write(count + ", " +   subtask.SubTasktoString());
                count++;
                fileWriter.write("\n");
            }
        } catch (IOException exception) {
            System.out.println("Ошибка при записи в файл.");
            exception.getStackTrace();
        }
        fileWriter.close();
    }


}
