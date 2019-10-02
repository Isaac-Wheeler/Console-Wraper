package mcc.server.player;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.URL;
import java.net.URLConnection;
import java.time.Instant;

import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

/*Class is not intended to be used as an instance. 
* Please copy methods inside your own plugin as this does not
* contain a constructor, no one is stopping you from adding one
* though. 
* This code is free to use for everyone, forever.
*
* Source : https://gist.github.com/intronate67/1a1d503273304494d8b2d19034857ea7
*
*/
public class UUIDGetter{
	
    public static String getUUID(String name){
	    if(isAPIOnline()){
	        try{
	            URL url = new URL("https://api.mojang.com/users/profiles/minecraft/" + name) ;// + "?at=" + Instant.now() .getEpochSecond());
	            URLConnection conn = url.openConnection();
	            BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
	            JsonElement je = new JsonParser().parse(rd.readLine());
	            //UUID without hyphens.
	            String sUUID = je.getAsJsonObject().get("id").getAsString();
				//UUID with hyphens, this is optional but this is the kind of UUID sponge uses.
	            String uuid = 
	                sUUID.substring(0, Math.min(sUUID.length(), 8))
	                    + "-"
	                    + sUUID.substring(8, Math.min(sUUID.length(), 12))
	                    + "-"
	                    + sUUID.substring(12, Math.min(sUUID.length(), 16))
	                    + "-"
	                    + sUUID.substring(16, Math.min(sUUID.length(), 20))
	                    + "-"
	                    + sUUID.substring(20, Math.min(sUUID.length(), 32));
	            rd.close();
	            //You can return sUUID or uuid, whichever you prefer.
	            return uuid;
	        }catch(Exception e){
	            //Print stack trace if wanted.
	            e.printStackTrace();
	        }
	    }
	    //Return servers are unavailable here
	    return null;
	}
    
    //And also to determine if Mojang API is available

	private static boolean isAPIOnline(){
	    try{
	        //2000 is just the timeout, in milliseconds, you can change it to what you want.
	        if(InetAddress.getByName("api.mojang.com").isReachable(2000)){
	            return true;
	        }
	    }catch(Exception e){
	        //Print stack trace if wanted.
	        e.printStackTrace();
	    }
	    return false;
	}
    //Use 'getUUID("USERNAME_HERE");
}