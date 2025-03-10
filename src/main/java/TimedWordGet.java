import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import org.json.JSONObject;

import java.awt.Color;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class TimedWordGet {

    public static void sendDailyWord(JDA jda){

        JSONObject json = WordFetch.fetchWord();
        String word = json.get("word").toString();
        String definition = json.get("definition").toString();
        handleFileWrite(word);
        TextChannel channel = jda.getTextChannelById(Main.channelId);
        channel.sendMessageEmbeds(
                new EmbedBuilder()
                        .setTitle("Word of the Day!")
                        .setDescription("Your daily word. Learn about it and use it in a sentence.")
                        .setColor(Color.CYAN)
                        .addField("Word", word, true)
                        .addField("Definition", definition, false).build()
        ).queue();

    }

    public static void handleFileWrite(String word){
        try{
            FileWriter writer = new FileWriter("word_day.txt");
            writer.write(word);
            writer.close();
        }catch(IOException e){
            e.getStackTrace();
        }
    }

    public static String handleFileRead(){
        String result = "";
        try{
            FileReader reader = new FileReader("word_day.txt");
            StringBuilder content = new StringBuilder();
            int i;
            while((i=reader.read())!=-1){
                content.append((char)i);
            }
            reader.close();
            result = content.toString();
        } catch(Exception e){
            e.getStackTrace();
        }
        return result;
    }

}
