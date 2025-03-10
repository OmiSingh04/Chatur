import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.requests.GatewayIntent;
import org.json.JSONObject;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.EnumSet;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class Main {

    public static String channelId;
    public static String geminiToken;

    public static void main(String[] args) {

        if(args.length < 3){
            System.out.println("Arguments missing... [bot token] [channel id] [gemini token]");
            System.exit(-1);
        }

        String token = args[0];
        channelId = args[1];
        geminiToken = args[2];

        JDA jda = JDABuilder.createLight(token, EnumSet.of(GatewayIntent.GUILD_MESSAGES, GatewayIntent.MESSAGE_CONTENT))
                .addEventListeners(new WordRegisterListener())
                .addEventListeners(new SubmitListener())
                .build();

        LocalDateTime now = LocalDateTime.now();
        LocalDateTime target = now.toLocalDate().atTime(
                14, 21
        );

//        LocalDateTime target = now.toLocalDate().atTime(
//                16, 22
//        );

        //add a day if the target for today is already missed.
        if(now.isAfter(target))
            target.plusDays(1);

        //Milliseconds between now and target time
        Long initialDelay = Duration.between(now, target).toMillis();
        Long period = TimeUnit.DAYS.toMillis(1); //1 day to millis

        System.out.println(initialDelay);
        System.out.println(target);

        ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();
        scheduler.scheduleAtFixedRate(
                () -> {
                    TimedWordGet.sendDailyWord(jda);
                },initialDelay,period, TimeUnit.MILLISECONDS
        );
    }
}