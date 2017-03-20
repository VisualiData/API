/**
 * Created by Gebruiker on 13-3-2017.
 */

import api.*;
import database.DBConnector;

import static spark.Spark.after;

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
        after((request, response) -> {
            response.type("application/json");
        });
    }

    public void stop(){
        stop();
        DBConnector.close();
    }
}
