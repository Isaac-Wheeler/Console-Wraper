package mcc;

import java.io.File;
import mcc.Exception.MissingFileException;
import mcc.common.Log;
import mcc.server.Server;
import mcc.configs.Config;
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

    public static void main(String[] args) throws InterruptedException {
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
        Config config = Config.getInstance();
        config.loadConfig();

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

        Server server1 = new Server(config);
        Thread t = new Thread(server1);
        t.start();
    }

    public static void createServer(int serverNumber, String jarFileName) throws MissingFileException {
        File directory = new File("Minecraft/server" + serverNumber);
        directory.mkdirs();
        File jarFile = new File(directory, jarFileName);

        if(jarFile.exists() != true){
            throw new MissingFileException("Missing jar File");
        }

        String[] args = {"-Xmx1024M ","-Xms1024M "}; //todo add java options, create via dynamic 

        //TODO add to config / load from config
        ConfigServer server = new ConfigServer(jarFile, directory, serverNumber, args);
        Config.getInstance().addServer(server);
    }

    public static void exit(int exitCode) {
        if(exitCode > 0){
            error.printlnOut("Exiting with error code: " + exitCode);
        }
        System.exit(exitCode);
    }

}
