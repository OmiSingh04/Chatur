import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.requests.GatewayIntent;

import java.time.LocalTime;
import java.util.Arrays;
import java.util.EnumSet;

public class Main {

    public static void main(String[] args) {
        String token = args[0];
        JDABuilder.createLight(token, EnumSet.of(GatewayIntent.GUILD_MESSAGES, GatewayIntent.MESSAGE_CONTENT))
                .addEventListeners(new WordRegisterListener())
                .build();
    }

}
