package task;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;

/**
 * This class reads file from the buffer and process it
 */
public class Consumer implements Runnable {

    /**
     * The buffer
     */
    private Buffer buffer;

    /**
     * Constructor of the class. Initialize the buffer
     *
     */
    public Consumer(Buffer buffer) {
        this.buffer = buffer;
    }

    /**
     * Core method of the consumer. While there are pending files in the
     * buffer, try to read one.
     */
    @Override
    public void run() {
        while(buffer.hasPendingFiles()) {
            HashMap<String, String> file = buffer.get();
            for (String fileName : file.keySet()) {
                try {
                    PrintWriter writer = new PrintWriter("Output/"+fileName,"UTF-8");
                    writer.println(reverseCase(file.get(fileName)));
                    writer.close();
                    System.out.println("Written file:"+fileName);
                } catch (FileNotFoundException | UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
            }

        }
    }

    private static String reverseCase(String text)
    {
        char[] chars = text.toCharArray();
        for (int i = 0; i < chars.length; i++)
        {
            char c = chars[i];
            if (Character.isUpperCase(c))
            {
                chars[i] = Character.toLowerCase(c);
            }
            else if (Character.isLowerCase(c))
            {
                chars[i] = Character.toUpperCase(c);
            }
        }
        return new String(chars);
    }

}
