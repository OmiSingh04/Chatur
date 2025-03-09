import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.entities.channel.Channel;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;

import java.awt.*;

public class TimedWordGet {

    public static void sendDailyWord(JDA jda){

        String word = WordFetch.fetchWord();

        TextChannel channel = jda.getTextChannelById(Main.channelId);
        channel.sendMessageEmbeds(
                new EmbedBuilder()
                        .setTitle("Word of the Day!")
                        .setDescription("Your daily word. Learn about it and use it in a sentence.")
                        .setColor(Color.CYAN)
                        .addField("Word", word, true).build()
        ).queue();

    }

}
