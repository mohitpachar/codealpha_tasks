package nlp;

import org.json.JSONArray;
import org.json.JSONObject;
import utils.JSONReader;

import java.util.HashMap;

public class ChatEngine {
    private HashMap<String, String> faqMap;

    public ChatEngine(String filePath) {
        faqMap = new HashMap<>();
        JSONArray faqArray = JSONReader.readJSONFile(filePath);
        for (int i = 0; i < faqArray.length(); i++) {
            JSONObject obj = faqArray.getJSONObject(i);
            faqMap.put(obj.getString("question").toLowerCase(), obj.getString("answer"));
        }
    }

    public String getResponse(String input) {
        input = input.trim().toLowerCase();
        return faqMap.getOrDefault(input, "I'm still learning. Can you rephrase that?");
    }
}