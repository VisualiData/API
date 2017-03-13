/**
 * Created by Gebruiker on 13-3-2017.
 */

import API.*;

import static spark.Spark.*;

public class Application{
    public static void main (String[] args){
        start();
    }

    public static void start(){
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
    }
}
