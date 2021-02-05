import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.text.DecimalFormat;
import java.util.*;

public class Parser implements AutoCloseable {
    private final Map<String, Integer> count;
    private final List<Map.Entry<String, Integer>> sortedList;
    private final Reader reader;
    private final Writer writer;
    private int totalRead = 0;

    public Parser(Reader input, Writer output) {
        reader = input;
        writer = output;
        sortedList = new LinkedList<>();
        count = new HashMap<>();
    }

    public void read() throws IOException {
        StringBuilder stringBuilder = new StringBuilder();
        int i;
        while ((i = reader.read()) != -1) {
            char c = (char)i;
            if (Character.isLetterOrDigit(c)) {
                stringBuilder.append(c);
            } else if (!stringBuilder.isEmpty()) {
                String currentWord = stringBuilder.toString();
                int currentCount;

                if(count.containsKey(currentWord))
                    currentCount = count.get(currentWord) + 1;
                else
                    currentCount = 1;

                count.put(currentWord, currentCount);
                stringBuilder = new StringBuilder();
                totalRead++;
            }
        }

        if (!stringBuilder.isEmpty()) {
            String currentWord = stringBuilder.toString();
            int currentCount;
            if(count.containsKey(currentWord))
                currentCount = count.get(currentWord) + 1;
            else
                currentCount = 1;
            count.put(currentWord, currentCount);
        }
    }

    public void write() throws IOException {
        sortedList.addAll(count.entrySet());
        sortedList.sort((first, second) -> -(first.getValue().compareTo(second.getValue())));

        for (Map.Entry <String, Integer> currentEntry : sortedList) {
            writer.write(currentEntry.getKey() + " " + currentEntry.getValue() + " " +
                    new DecimalFormat("#0.00000").format((double)currentEntry.getValue() / totalRead) + '\n');
        }
    }

    @Override
    public void close() throws Exception {
        reader.close();
        writer.close();
    }
}