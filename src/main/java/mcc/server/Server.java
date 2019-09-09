package mcc.server;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.ArrayList;

import mcc.App;
import mcc.common.Log;
import mcc.server.player.Ops;
import mcc.server.player.Player;

public class Server implements Runnable {

    /**
     *
     */

    File jarFile, directory;
    int serverNumber;
    Log normal, error, inputLog;
    String msg;
    Process process;
    String[] args;
    ArrayList<Player> players;
    ArrayList<Ops> ops;

    State serverState;

    public void setState(State state) {
        serverState = state;
    }

    public State getState() {
        return serverState;
    }

    public Server(File jarFile, int serverNumber, File directory, String[] args) {
        this.jarFile = jarFile;
        this.serverNumber = serverNumber;
        this.directory = directory;
        this.args = args;
        this.players = new ArrayList<Player>();
        this.ops = new ArrayList<Ops>();

        // Create log Files for server
        File logLocation = new File("Minecraft/Log");
        logLocation.mkdirs();
        normal = new Log("server" + serverNumber + "-normal", logLocation);
        error = new Log("server" + serverNumber + "-error", logLocation);
        inputLog = new Log("server" + serverNumber + "-input", logLocation);
    }

    

    public void writeCommand(String msg) {
        this.msg = msg;
    }

    public void StopServer() {
        writeCommand("stop");
    }

    public void ForceStopServer() {
        process.destroyForcibly();
    }

    @Override
    public void run() {

        ProcessBuilder pb = new ProcessBuilder();

        StringBuilder javaCommand = new StringBuilder();
        javaCommand.append("java ");
        for (String var : args) {
            javaCommand.append(var);
        }
        javaCommand.append("-jar ");
        javaCommand.append("\"" + jarFile.getAbsolutePath() + "\"");
        // javaCommand.append(" nogui"); TODO: undo

        if (App.isWindows) {//TODO: replace with settings.java
            pb.command("cmd.exe", "/c", javaCommand.toString());
        } else {
            pb.command("bash", "-c", javaCommand.toString());
        }

        pb.directory(directory);

        try {

            process = pb.start();

            serverState = State.STARTED;

            ProcessServerOutput processOutput = new ProcessServerOutput(this);

            new Thread(new ProcessInput(process.getInputStream(), normal, processOutput)).start();
            new Thread(new ProcessInput(process.getErrorStream(), error, processOutput)).start();

            try {
                BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(process.getOutputStream()));
                String input = null;
                while (process.isAlive()) {
                    if (serverState == State.RUNNING) {
                        input = null;
                        if ((input = msg) != null) {
                            if (input == "stop") {
                                serverState = State.STOPPING;
                            }
                            writer.write(input);
                            writer.newLine();
                            writer.flush();
                            msg = null;
                        }
                    }

                }
            } catch (IOException e) {
                e.printStackTrace();
            }

            int exitCode = process.waitFor();

            serverState = State.STOPPED;

            System.out.println("\nExited with error code : " + exitCode);

        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    private class ProcessInput implements Runnable {
        InputStream input;
        Log log;
        ProcessServerOutput processOutput;

        public ProcessInput(InputStream input, Log log, ProcessServerOutput processOutput) {
            this.input = input;
            this.log = log;
            this.processOutput = processOutput;
        }

        @Override
        public void run() {
            BufferedReader reader = new BufferedReader(new InputStreamReader(input));
            String line;
            try {
                while ((line = reader.readLine()) != null) {
                    log.println(line);
                    processOutput.processLine(line);
                }
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }

    }

}