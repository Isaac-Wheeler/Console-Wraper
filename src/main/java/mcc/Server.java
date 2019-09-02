package mcc;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;

public class Server implements Runnable {

    /**
     *
     */

    private static final String JAVA_RUN_COMMAND = "java -jar \"";
    File jarFile;
    int serverNumber;
    File directory;
    Log normal;

    OutputStream output;

    public Server(File jarFile, int serverNumber, File directory) {
        this.jarFile = jarFile;
        this.serverNumber = serverNumber;
        this.directory = directory;

        // Create log Files for server
        File logLocation = new File("Minecraft/Log");
        logLocation.mkdirs();
        normal = new Log("server" + serverNumber + "-normal", logLocation);
    }

    @Override
    public void run() {

        ProcessBuilder pb = new ProcessBuilder();

        String javaCommand = JAVA_RUN_COMMAND + jarFile.getAbsolutePath() + "\""; // TODO java arguments ie: ram sizes;

        if (App.isWindows) {
            pb.command("cmd.exe", "/c", javaCommand);
            pb.directory(directory);
        } else {
            // TODO: non windows command
        }

        try {

            Process process = pb.start();

            output = process.getOutputStream();

            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));

            String line;
            while ((line = reader.readLine()) != null) {
                normal.println(line);
            }

            int exitCode = process.waitFor();
            System.out.println("\nExited with error code : " + exitCode);

        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

}