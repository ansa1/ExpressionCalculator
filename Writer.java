import com.sun.xml.internal.ws.api.ha.StickyFeature;

import java.io.FileNotFoundException;
import java.io.PrintWriter;

public class Writer {
    public static final String LINE_SEPARATOR = "\r\n";

    private PrintWriter printWriter;

    public Writer(String path) {
        try {
            printWriter = new PrintWriter(path);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void writeLine(long line) {
        printWriter.print(line + LINE_SEPARATOR);
    }

    public void writeLine(String line) {
        printWriter.print(line + LINE_SEPARATOR);
    }

    public void close() {
        printWriter.close();
    }
}
