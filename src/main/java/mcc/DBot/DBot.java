package mcc.DBot;

import java.util.ArrayList;

import javax.security.auth.login.LoginException;

import net.dv8tion.jda.api.AccountType;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import mcc.App;
import mcc.configs.Config;

public class DBot extends ListenerAdapter {

    ArrayList<String> lines;
    private static DBot single_instance = null;
    private JDA api;

    private DBot() {
        lines = new ArrayList<String>();
    }

    public static DBot getInstance() {
        if (single_instance == null) {
            single_instance = new DBot();
        }
        return single_instance;
    }

    public static void main() throws LoginException {
        JDABuilder builder = new JDABuilder(AccountType.BOT);
        String token = Config.getInstance().getDicord_bot_Token_string();
        builder.setToken(token);
        builder.addEventListeners(getInstance());
        DBot dbot = getInstance();
        dbot.api = builder.build();
    }

    @Override
    public void onMessageReceived(MessageReceivedEvent event) {
        if (event.getAuthor().isBot()) {
            return;
        }
        if (event.getMessage().getContentRaw().equals("!ping")) {
            event.getChannel().sendMessage("Pong!").queue();
        }
        if (event.getMessage().getContentRaw().equals("!quit")) {
            App.exit(0);
        }
        if (event.getMessage().getContentRaw().equals("!console")) {
            event.getChannel().sendMessage("channel ID: " + event.getChannel().getId()).queue();
        }
    }

    public void writeConsole(String channelID, String message) {
        MessageChannel console = api.getTextChannelById(channelID);
        console.sendMessage(message).queue();
    }

}