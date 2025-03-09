import org.json.JSONArray;
import org.json.JSONObject;

import java.net.*;
import java.io.*;
import org.json.JSONObject;

public class WordFetch {

    public static String fetch(String urlString){
        StringBuilder output = new StringBuilder();
        try{
            URL url = new URL(urlString);
            URLConnection connection = url.openConnection();
            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String inputLine;
            while((inputLine = in.readLine())!=null){
                output.append(inputLine);
            }
        }catch(Exception e){
            System.err.println(e);
        }
        return output.toString();
    }
    public static JSONObject fetchWord(){
        //** Your task **//
        String wordResponse = fetch("https://random-word-api.vercel.app/api?words=1");
        String word = wordResponse.substring(2,wordResponse.length()-2);
        String url = "https://api.dictionaryapi.dev/api/v2/entries/en/"+word;
        String dictionaryResponse = fetch(url);
        JSONArray json  = new JSONArray(dictionaryResponse);
        JSONObject result = new JSONObject();
        for(int i = 0 ; i < json.length() ; i++){
            JSONObject obj = json.getJSONObject(i);
            result.put("word", word);
            JSONArray objArray = new JSONArray(obj.get("meanings").toString());
            JSONObject deal = objArray.getJSONObject(0);
            JSONArray objArray1 = new JSONArray(deal.get("definitions").toString());
            JSONObject def = objArray1.getJSONObject(0);
            result.put("definition", def.get("definition"));
        }
        return result;
    }
}
