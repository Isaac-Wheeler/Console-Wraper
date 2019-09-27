package mcc.configs;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import com.google.gson.*;

public class Config{
    //static variable single_instance to create singleton;
    private static Config single_instance = null;

    //variables
    private String created;
    private int number_of_servers;
    private ArrayList<ConfigServer> servers;

    //private constructor
    private Config(){
        this.servers = new ArrayList<ConfigServer>();
    }

    public static Config getInstance(){
        if(single_instance == null){
            single_instance = new Config();
        }
        return single_instance;
    }


    public void saveConfig(){
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        Date mydate = new Date();

        created = mydate.toString();

        try(FileWriter writer = new FileWriter("config.json")){
            gson.toJson(this, writer);
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    public void loadConfig(){
        Gson gson = new Gson();

        try {
            Config copy = gson.fromJson(new FileReader("config.json"), Config.class);
            this.created = copy.created;
            this.number_of_servers = copy.number_of_servers;
            this.servers = copy.servers;
            //TODO: keep adding varaibles here;

		} catch (JsonSyntaxException | JsonIOException | FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
    }


    public ArrayList<ConfigServer> getServers(){
        return this.servers;
    }

    public void addServer(ConfigServer server){
        this.servers.add(server);
        saveConfig();
    }

    public void setServers(ArrayList<ConfigServer> servers){
        this.servers = servers;
        saveConfig();
    }

    public int getNumber_of_servers() {
        return this.number_of_servers;
    }

    public void setNumber_of_servers(int number_of_servers) {
        this.number_of_servers = number_of_servers;
        saveConfig();
    }

}