package mcc;

import java.io.PrintWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Log 
{
    PrintWriter out;

    public Log ( String logName, File logLocation){
        this.createLog(logName, logLocation);
    }

    public void printlnOut( String msg ){
        System.out.println( msg );
        println( msg );
    }

    public void printOut( String msg ){
        System.out.print( msg );
        print( msg );
    }

    public void println( String msg ){
        out.println( msg );
        out.flush();
    }

    public void print( String msg ){
        out.print( msg );
        out.flush();
    }

    public void close(){
        out.close();
    }

    private void createLog(String logName, File logLocation){
        DateFormat dateFormat = new SimpleDateFormat("MM-dd-yyyy");
        Date date = new Date();
        try {
            File log = new File(logLocation, logName + dateFormat.format(date) + ".txt");
            this.out = new PrintWriter(new FileWriter(log, true));
        }catch (IOException e) {
			System.err.println(e.getMessage());
			e.printStackTrace();
            System.exit(1);
        }
    }
}