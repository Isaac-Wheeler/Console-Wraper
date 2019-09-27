package mcc.configs;

import java.io.File;

public class ConfigServer {

    File jarFile, directory;
    int serverNumber;
    String[] args;



    public ConfigServer(File jarFile, File directory, int serverNumber, String[] args) {
        this.jarFile = jarFile;
        this.directory = directory;
        this.serverNumber = serverNumber;
        this.args = args;
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


}