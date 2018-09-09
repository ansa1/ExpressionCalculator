public class Program {

    public static final String inputFile = "../in.txt";
    public static final String outputFile = "../out.txt";

    /*
     Launches the Expression Calculator which reads the input file line by line and writes to the output file line by line.
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

}
