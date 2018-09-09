import java.io.*;
import java.util.Scanner;

public class Program {

    public static final String inputFile = "in11.txt";
    public static final String outputFile = "out.txt";


    /*
     Launches the parser.
     */
    public static void main(String[] args) {
        Reader reader = new Reader(inputFile);
        Writer writer = new Writer(outputFile);
        String line = "";
        while ((line = reader.readLine()) != null) {
            Parser parser = new Parser(line);
            Expression expressionTree = parser.parse();
            writer.writeLine(expressionTree.calculate());
        }
        writer.close();
    }

    /**
     * Converts the given json string to one line.
     *
     * @param json the given json string.
     * @return obtained json string in one line.
     */
    public static String JSONtoLine(String json) {
        return json.replace("\n", "").replace("\t", "");
    }

    /**
     * Reads a line with an expression from the input file.
     *
     * @return the given line without spaces.
     */
    public static String readLine() {
        String input = "";
        File file = new File(inputFile);
        try {
            Scanner sc = new Scanner(file);
            if (sc.hasNextLine()) {
                input = sc.nextLine();
            }
            sc.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return input.replace(" ", "");
    }
}
