import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.json.JSONObject;

import java.awt.*;

public class SubmitListener extends ListenerAdapter {
    //on submit - get it done.
    @Override
    public void onMessageReceived(MessageReceivedEvent event){
        if(event.getAuthor().isBot()) return;

        if(event.getMessage().getContentDisplay().startsWith("!sentence")){
            String word = TimedWordGet.handleFileRead();
            TextChannel channel = event.getChannel().asTextChannel();
            if(word.isEmpty()){
                channel.sendMessage("The word is not yet set.").queue();
                return;
            }
            String[] messageParts = event.getMessage().getContentDisplay().split(" ", 2);
            String sentence = messageParts[1];
            if(!sentence.contains(word)){
                channel.sendMessage("Use the word in the sentence : " + word).queue();
                return;
            }

            JSONObject result = SentenceCheck.score(sentence, word);
            channel.sendMessageEmbeds(
                    new EmbedBuilder()
                            .setTitle("Your Score")
                            .setColor(Color.CYAN)
                            .addField("Score", result.get("score").toString(), true)
                            .addField("Reason", result.getString("message"), false)
                            .build()
            ).queue();
        }
        if(event.getMessage().getContentDisplay().startsWith("!word")){
            String word = TimedWordGet.handleFileRead();
            TextChannel channel = event.getChannel().asTextChannel();
            channel.sendMessage("\0"+word).queue();

        }
    }

}