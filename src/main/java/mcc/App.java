package mcc;

import java.io.File;
import java.util.ArrayList;

import javax.security.auth.login.LoginException;

import mcc.DBot.DBot;
import mcc.Exception.MissingFileException;
import mcc.common.Log;
import mcc.server.Server;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import mcc.configs.Config;
import mcc.configs.ConfigDiscord;
import mcc.configs.ConfigServer;

/**
 * Hello world!
 *
 */
public class App {

    /**
     *
     */

    private static final String LOG_LOCATION = "Log";
    private static final String CURRENT_SYSTEM_OS_MSG = "Current System os is : ";
    public static Log error;
    public static Log normal;
    public static boolean isWindows = false;
    public static ArrayList<Server> servers;

    public static void main(String[] args) throws InterruptedException {
        
        //Initialze globle Varables
        servers = new ArrayList<Server>();

        //Create Log Files
        
        File logLocation = new File(LOG_LOCATION);
        error = new Log("error", logLocation);
        normal = new Log("normal", logLocation);
        String os = System.getProperty("os.name").toLowerCase();
        if (os.contains("windows")) {
            isWindows = true;
        }
        String msg = CURRENT_SYSTEM_OS_MSG + os;
        error.println(msg);
        normal.printlnOut(msg);

        //Load Config

        Config config = Config.getInstance();
        config.loadConfig();

        //Discord Start

        if(config.getConfigDiscord().isEnable_discord()){
            try {
                DBot.main();
            } catch (LoginException e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
            }
        } 
        
        //Start configured servers

        try {
            for (ConfigServer configServer : config.getServers()) {
                startServer(configServer);
            }
        } catch (MissingFileException e) {
            // TODO handle better
            e.printStackTrace();
            error.printlnOut(e.getMessage());
        }
        

    }

    public static void startServer(ConfigServer config) throws MissingFileException{

        if(config.getDirectory().exists() != true){
            throw new MissingFileException("Missing Server Directory");
        }
        if(config.getJarFile().exists() != true){
            throw new MissingFileException("Missing jar File");
        }

        Server server = new Server(config);
        servers.add(server);
        Thread t = new Thread(server);
        t.start();
    }

    public static void createServer(int serverNumber, String jarFileName) throws MissingFileException {

        //TODO dynamic server numbering

        File directory = new File("Minecraft/server" + serverNumber);
        directory.mkdirs();
        File jarFile = new File(directory, jarFileName);

        if(jarFile.exists() != true){
            throw new MissingFileException("Missing jar File");
        }

        String[] args = {"-Xmx1024M ","-Xms1024M "}; //todo add java options, create via dynamic 

        //TODO add to config / load from config
        ConfigServer server = new ConfigServer(jarFile, directory, serverNumber, args, "627358163217416203");
        Config.getInstance().addServer(server);
    }

    public static void exit(int exitCode) {
        if(exitCode > 0){
            error.printlnOut("Exiting with error code: " + exitCode);
        }
        System.exit(exitCode);
    }

}
