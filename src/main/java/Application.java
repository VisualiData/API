/*
 * Main class for initializing API
 */

import api.*;
import database.DBConnector;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import util.ResponseUtil;

import static spark.Spark.*;

public class Application{
    private static final Logger LOGGER = LogManager.getLogger(Application.class);
    private Application(){}

    public static void main (String[] args){
        init();
        start();
    }

    private static void start(){
        DBConnector.initDB();
        IURL[] addUrls = {
                new AddSensorUrl(),
                new UpdateSensorUrl(),
                new DeleteSensorUrl(),
                new GetAllSensorsUrl(),
                new GetSensorDataUrl(),
                new PostSensorDataUrl(),
                new FormatDataBaseUrl(),
                new GetSensorURL()
            };
        for (IURL url : addUrls){
            url.openUrl();
        }
        errorHandling();
        before((request, response) -> LOGGER.debug(request.requestMethod() + " request made to: " + request.uri()));
        after((request, response) -> response.type("application/json"));
    }


    private static void errorHandling(){
        notFound(ResponseUtil.generateFailed("Not Found", 404).toJSONString());
        internalServerError(ResponseUtil.generateFailed("Internal server error", 500).toJSONString());
    }
}
