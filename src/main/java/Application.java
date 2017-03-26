/**
 * Created by Gebruiker on 13-3-2017.
 */

import api.*;
import database.DBConnector;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import static spark.Spark.after;
import static spark.Spark.before;

public class Application{
    private static final Logger LOGGER = LogManager.getLogger(Application.class);
    public static void main (String[] args){
        start();
    }

    public static void start(){
        DBConnector.initDB();
        IURL[] addUrls = {
                new AddSensorUrl(),
                new UpdateSensorUrl(),
                new DeleteSensorUrl(),
                new GetAllSensorsUrl(),
                new GetSensorDataUrl(),
                new PostSensorDataUrl(),
                new FormatDataBaseUrl()
        };
        for (IURL url : addUrls){
            url.openUrl();
        }
        before((request, response) -> LOGGER.debug(request.requestMethod() + " request made to: " + request.uri()));
        after((request, response) -> response.type("application/json"));
    }

    public void stop(){
        stop();
        DBConnector.close();
    }
}
