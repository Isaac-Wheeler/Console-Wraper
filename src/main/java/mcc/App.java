package mcc;

import java.io.File;
import mcc.Exception.MissingFileException;

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

        try {
            createServer(1, "server.jar");
        } catch (MissingFileException e) {
            // TODO handle better
            e.printStackTrace();
            error.printlnOut("missing jar file please add to correct directory");
        }

    }

    public static void createServer(int serverNumber, String jarFileName) throws MissingFileException {
        File directory = new File("Minecraft/server" + serverNumber);
        directory.mkdirs();
        File jarFile = new File(directory, jarFileName);

        if(jarFile.exists() != true){
            throw new MissingFileException("Missing jar File");
        }

        Server server1 = new Server(jarFile, serverNumber, directory);
        Thread t = new Thread(server1);
        t.start();
    }

    public static void exit(int exitCode) {
        System.exit(exitCode);
    }

}
