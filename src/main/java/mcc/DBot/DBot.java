package mcc.DBot;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import javax.security.auth.login.LoginException;

import net.dv8tion.jda.api.AccountType;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Category;
import net.dv8tion.jda.api.entities.EmbedType;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.GuildChannel;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.requests.restaction.ChannelAction;
import net.dv8tion.jda.api.requests.restaction.RoleAction;
import java.awt.Color;
import java.io.IOException;
import java.io.InputStream;

import mcc.App;
import mcc.configs.Config;
import mcc.configs.ConfigDiscord;
import mcc.configs.ConfigServer;
import mcc.server.Server;
import mcc.server.State;

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
        String token = Config.getInstance().getConfigDiscord().getDicord_bot_Token_string();
        builder.setToken(token);
        builder.addEventListeners(getInstance());
        DBot dbot = getInstance();
        dbot.api = builder.build();
        try {
            dbot.startup();
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

    private void startup() throws InterruptedException {
        api.awaitReady();
        createServerChannels();
        createServerRole();
    }

    private void createServerChannels() {
        Category c = api.getCategoryById(Config.getInstance().getConfigDiscord().getDiscord_bot_server_category_id());
        ArrayList<ConfigServer> configServers = Config.getInstance().getServers();
        for (ConfigServer var : configServers) {
            if (var.getChannelID() == null || var.getChannelID().equals("")) {
                ChannelAction<TextChannel> channel = c.createTextChannel("server" + var.getServerNumber() + "-console");
                var.setChannelID(channel.complete().getId());
                Config.getInstance().updateConfig();
            }
        }
    }

    private void createServerRole() {
        String minecraft_console_id = Config.getInstance().getConfigDiscord().getMinecraft_console_role_id();
        if (minecraft_console_id == null || minecraft_console_id.equals("")) {
            Guild guild = api.getGuildById(Config.getInstance().getConfigDiscord().getDiscord_bot_Guild_id());
            RoleAction role = guild.createRole();
            role.setName("Minecraft-Console");
            Config.getInstance().getConfigDiscord().setMinecraft_console_role_id(role.complete().getId());
            Config.getInstance().updateConfig();
        }
    }

    @Override
    public void onMessageReceived(MessageReceivedEvent event) {
        // Make sure its not a bot talking
        if (event.getAuthor().isBot()) {
            return;
        }
        // Put static based command handles in this method
        if (staticMessageHandles(event)) {
            return;
        }
        // Put server exact command handles in this method
        if (serverConsoleMessageHandles(event)) {
            return;
        }

    }

    public boolean serverConsoleMessageHandles(MessageReceivedEvent event) {

        String channel = event.getChannel().getId();
        Boolean isCommand = false;
        if (event.getMessage().getContentRaw().startsWith("//")) {
            isCommand = true;
        }
        User user = event.getAuthor();
        if (verifyUserPermissions(user)) {
            ArrayList<ConfigServer> configServers = Config.getInstance().getServers();
            for (ConfigServer var : configServers) {
                if (var.getChannelID().equals(channel)) {
                    if (isCommand) {
                        App.getServers().get(var.getServerNumber() - 1)
                                .writeCommand(event.getMessage().getContentRaw().substring(1));
                        return true;
                    } else {
                        App.getServers().get(var.getServerNumber() - 1)
                                .writeCommand("/say " + user.getName() + ": " + event.getMessage().getContentRaw());
                        return true;
                    }

                }
            }

        }

        return false;
    }

    public boolean staticMessageHandles(MessageReceivedEvent event) {
        String message = event.getMessage().getContentRaw();
        if (message.equals("!ping")) {
            event.getChannel().sendMessage("Pong!").queue();
            return true;
        }
        if (message.equals("!quit")) {
            App.exit(0);
            return true;
        }
        if (message.equals("!console")) {
            event.getChannel().sendMessage("channel ID: " + event.getChannel().getId()).queue();
            return true;
        }
        if (message.equals("!list servers")) {
            MessageChannel mChannel = event.getChannel();
            EmbedBuilder embedBuilder = new EmbedBuilder();
            embedBuilder.setAuthor("Minecraft Control Panel");
            for (Server var : App.getServers()) {
                embedBuilder.addField("Server Number", var.getConfigServer().getServerNumber() + "", true);
                embedBuilder.addField("Server Name", var.getConfigServer().getServerName(), true);
                embedBuilder.addField("Server State", var.getState().toString(), true);
            }
            mChannel.sendMessage("server list").embed(embedBuilder.build()).queue();
            return true;
        }
        if (message.contains("!stop server")){
            event.getChannel().sendMessage("hit").queue();
            return true;
        }
        return false;
    }

    public boolean verifyUserPermissions(User user) {
        ConfigDiscord configDiscord = Config.getInstance().getConfigDiscord();
        Guild guild = api.getGuildById(configDiscord.getDiscord_bot_Guild_id());
        Role role = guild.getRoleById(configDiscord.getMinecraft_console_role_id());

        if (guild.getMember(user).getRoles().contains(role)) {
            return true;
        }
        return false;
    }

    public void writeConsole(String channelID, String message) {
        MessageChannel console = api.getTextChannelById(channelID);
        console.sendMessage(message).queue();
    }

    public void writeImage(String channelID, String url, String description) {
        try {

            MessageChannel console = api.getTextChannelById(channelID);
            EmbedBuilder eb = new EmbedBuilder();
            eb.setDescription(description);
            InputStream file = new URL(url).openStream();
            eb.setImage("attachment://player.png");
            console.sendFile(file, "player.png").embed(eb.build()).queue();

        } catch (MalformedURLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

}