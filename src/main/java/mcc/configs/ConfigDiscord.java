package mcc.configs;

public class ConfigDiscord {

    private String dicord_bot_Token_string;
    private Boolean enable_discord;
    private String discord_bot_server_category_id;
    private String discord_bot_Guild_id;
    private String minecraft_console_role_id;

    public String getMinecraft_console_role_id() {
        return this.minecraft_console_role_id;
    }

    public void setMinecraft_console_role_id(String minecraft_console_role_id) {
        this.minecraft_console_role_id = minecraft_console_role_id;
    }

    public String getDicord_bot_Token_string() {
        return this.dicord_bot_Token_string;
    }

    public void setDicord_bot_Token_string(String dicord_bot_Token_string) {
        this.dicord_bot_Token_string = dicord_bot_Token_string;
    }

    public Boolean isEnable_discord() {
        return this.enable_discord;
    }

    public Boolean getEnable_discord() {
        return this.enable_discord;
    }

    public void setEnable_discord(Boolean enable_discord) {
        this.enable_discord = enable_discord;
    }

    public String getDiscord_bot_server_category_id() {
        return this.discord_bot_server_category_id;
    }

    public void setDiscord_bot_server_category_id(String discord_bot_server_category_id) {
        this.discord_bot_server_category_id = discord_bot_server_category_id;
    }

    public String getDiscord_bot_Guild_id() {
        return this.discord_bot_Guild_id;
    }

    public void setDiscord_bot_Guild_id(String discord_bot_Guild_id) {
        this.discord_bot_Guild_id = discord_bot_Guild_id;
    }

}