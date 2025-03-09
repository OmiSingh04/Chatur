import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.util.List;
import java.util.function.Predicate;

public class WordRegisterListener extends ListenerAdapter {


    @Override
    public void onMessageReceived(MessageReceivedEvent event){
        if(event.getAuthor().isBot()) return;

        if(event.getMessage().getContentDisplay().startsWith("!wordRegister")){
            List<Role> roles = event.getMember().getRoles();
            if(roles != null){
                if(!roles.stream().map(Role::getIdLong).anyMatch(Predicate.isEqual(1348231651582410793L))){
                    String roleName = event.getGuild().getRoleById(1348231651582410793L).getName();
                    event.getGuild().addRoleToMember(event.getMember(), event.getGuild().getRoleById(1348231651582410793L)).queue();
                    event.getChannel().sendMessage("Role Added").queue();
                }
                else{
                    event.getChannel().sendMessage("You already have it, dumbass.").queue();
                }
            }

        }
    }

}
