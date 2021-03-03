import java.io.BufferedReader;
import java.io.FileReader;

public class Utils {
    public static String loadResource(String fileName) throws Exception {
        StringBuilder result = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName));){
            String line;
            while ((line = reader.readLine()) != null){
                result.append(line);
                result.append("\n");
            }
        }
        return result.toString();
    }
}
