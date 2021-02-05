import java.io.*;

public class Main {
    public static void main(String[] args) {
        try (Parser parser = new Parser(new InputStreamReader(new FileInputStream(args[0])),
                                        new OutputStreamWriter(new FileOutputStream(args[1])))) {
            parser.read();
            parser.write();
        } catch (Exception e) {
            System.err.println("Error while reading file: " + e.getLocalizedMessage());
        }
    }
}
