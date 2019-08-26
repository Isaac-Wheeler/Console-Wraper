package mcc;

import java.io.File;

/**
 * Hello world!
 *
 */
public class App 
{

    
    /**
     *
     */
    
    private static final String LOG_LOCATION = "Log";
    private static final String CURRENT_SYSTEM_OS_MSG = "Current System os is : ";
    public static Log error;
    public static Log normal;

    public static void main( String[] args )
    {
    File logLocation = new File(LOG_LOCATION);
     error = new Log("error", logLocation);
     normal = new Log("normal", logLocation);  
     String msg = CURRENT_SYSTEM_OS_MSG + System.getProperty("os.name").toLowerCase();
     error.println(msg);
     normal.printlnOut(msg);
    }

    public static void exit( int exitCode )
    {
        System.exit( exitCode );
    }



}
