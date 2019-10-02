package mcc.configs;

import java.io.File;

public class ConfigServer {

    File jarFile, directory;
    int serverNumber;
    String[] args;
    String channelID;
    String serverName;

    public ConfigServer(File jarFile, File directory, int serverNumber, String[] args, String channelID) {
        this.jarFile = jarFile;
        this.directory = directory;
        this.serverNumber = serverNumber;
        this.args = args;
        this.channelID = channelID;
    }

    public File getJarFile() {
        return this.jarFile;
    }

    public void setJarFile(File jarFile) {
        this.jarFile = jarFile;
    }

    public File getDirectory() {
        return this.directory;
    }

    public void setDirectory(File directory) {
        this.directory = directory;
    }

    public int getServerNumber() {
        return this.serverNumber;
    }

    public void setServerNumber(int serverNumber) {
        this.serverNumber = serverNumber;
    }

    public String[] getArgs() {
        return this.args;
    }

    public void setArgs(String[] args) {
        this.args = args;
    }


    public String getChannelID() {
        return this.channelID;
    }

    public void setChannelID(String channelID) {
        this.channelID = channelID;
    }

    public String getServerName() {
        return this.serverName;
    }

    public void setServerName(String serverName) {
        this.serverName = serverName;
    }


}