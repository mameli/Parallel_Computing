package task;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Random;

/**
 * This class gets files and stores them in the
 * buffer, if there is space in it.
 */
public class Producer implements Runnable {

    private String path;
    /**
     * Buffer
     */
    private Buffer buffer;

    /**
     * Constructor of the class. Initialize the objects
     *
     * @param path   File path
     * @param buffer Buffer
     */
    public Producer(String path, Buffer buffer) {
        this.path = path;
        this.buffer = buffer;
    }

    @Override
    public void run() {
        buffer.setPendingFiles(true);
        File folder = new File(path);
        File[] listOfFiles = folder.listFiles();
        if (listOfFiles == null)
            return;
        for (File fileEntry : listOfFiles) {
            if (fileEntry.isDirectory())
                try {
                    readFolderFiles(fileEntry.getPath());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            if (fileEntry.isFile() && fileEntry.getName().toLowerCase().endsWith(".txt")) {
                try {
                    HashMap<String, String> file = new HashMap<>();
                    file.put(fileEntry.getName(),readFile(fileEntry.getPath(), StandardCharsets.UTF_8));
                    buffer.insert(file);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        buffer.setPendingFiles(false);
    }

    private static String readFile(String path, Charset encoding) throws IOException
    {
        byte[] encoded = Files.readAllBytes(Paths.get(path));
        return new String(encoded, encoding);
    }


    private void readFolderFiles(String folderPath) throws IOException {
        HashMap<String, String> files = new HashMap<>();
        File folder = new File(folderPath);
        File[] listOfFiles = folder.listFiles();
        for (File file : listOfFiles != null ? listOfFiles : new File[0]) {
            if (file.isDirectory())
                readFolderFiles(file.getPath());
            if (file.isFile() && file.getName().toLowerCase().endsWith(".txt")) {
                files.put(file.getName(), readFile(file.getPath(), StandardCharsets.UTF_8));
            }
        }
        buffer.insert(files);
    }

}
