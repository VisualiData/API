/**
 * Created by Gebruiker on 13-3-2017.
 */

import API.*;
import database.DBConnector;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

public class Application{
    public static void main (String[] args){
        start();
    }

    public static void start(){
        DBConnector.initDB();
        IURL[] addUrls = {
                new AddSensorUrl(),
                new DeleteSensorUrl(),
                new GetAllSensorsUrl(),
                new GetSensorDataUrl(),
                new PostSensorDataUrl()
        };
        for (IURL url : addUrls){
            url.OpenUrl();
        }

    }

    public void stop(){
        stop();
        DBConnector.close();
    }
}
