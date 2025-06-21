package utils;

import org.json.JSONArray;
import org.json.JSONTokener;
import java.io.FileInputStream;
import java.io.IOException;

public class JSONReader {
    public static JSONArray readJSONFile(String path) {
        try (FileInputStream fis = new FileInputStream(path)) {
            return new JSONArray(new JSONTokener(fis));
        } catch (IOException e) {
            e.printStackTrace();
            return new JSONArray();
        }
    }
}