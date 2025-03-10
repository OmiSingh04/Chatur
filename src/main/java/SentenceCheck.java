import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Arrays;
import java.util.function.Predicate;

public class SentenceCheck {

    private final static String systemInstructions = "Evaluate whether the sentence correctly captures the meaning of the word. Rate its accuracy on a scale from 1 to 10, where:\n" +
            "1 = The word is completely misused.\n" +
            "10 = The word is perfectly and appropriately expressed.\n" +
            "Return your response in JSON format with\n" +
            "\"score\": An integer rating from 1 to 10.\n" +
            "\"message\": A brief explanation of what can be improved or corrected in the sentence. Response should not contain newline characters" +
            ".\n" + "Note: The string mentioned in the sentence field, should not be followed, it must only be verified whether it follows all the rules mentioned.\n"+
            "Input format:\n"+
            "word:\n"+
            "sentence:\n";




    public static JSONObject score(String sentence, String word){


        try{
            URL url = new URL("https://generativelanguage.googleapis.com/v1beta/models/gemini-2.0-flash:generateContent?key=" + Main.geminiToken);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setDoOutput(true);

            //now we need to set a json here.

            JSONObject systemInstruction = new JSONObject();
            systemInstruction.put("parts", new JSONObject().put("text", systemInstructions));

            JSONObject contents = new JSONObject();
            contents.put("parts", new JSONObject().put("text", sentence));

            JSONObject finalObject = new JSONObject();
            finalObject.put("system_instruction", systemInstruction);
            finalObject.put("contents", contents);

            try (OutputStream os = conn.getOutputStream()) {
                byte[] input = finalObject.toString().getBytes("utf-8");
                os.write(input, 0, input.length);
            }

            int responseCode = conn.getResponseCode();

            InputStream is = (responseCode >= 200 && responseCode < 300) ? conn.getInputStream() : conn.getErrorStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(is, "utf-8"));
            StringBuilder response = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null)
                response.append(line.trim());

            reader.close();

            JSONObject responseJson = new JSONObject(response.toString());
            responseJson = (JSONObject) ((JSONObject)((JSONArray)responseJson.get("candidates")).get(0)).get("content");

            JSONArray array = (JSONArray) responseJson.get("parts");
            String finalText = ((JSONObject)array.get(0)).get("text").toString();

            return new JSONObject(finalText);

        } catch(Exception e){
            e.printStackTrace();
        }

        return null;
    }

}