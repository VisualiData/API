/*
 * Main class for initializing API
 */

import api.*;
import database.DBConnector;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import spark.Request;
import util.ResponseUtil;

import static spark.Spark.*;

public class Application{
    private static final Logger LOGGER = LogManager.getLogger(Application.class);
    private Application(){}

    public static void main (String[] args){
        staticFiles.location("/public");
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
        before((request, response) -> {
            LOGGER.debug(request.requestMethod() + " request made to: " + request.uri());
            if(!authenticated(request)){
                response.type("application/json");
                halt(401, ResponseUtil.generateFailed("Not authorized", 401).toString());
            }
        });
        after((request, response) -> response.type("application/json"));
    }


    private static void errorHandling(){
        notFound(ResponseUtil.generateFailed("Not Found", 404).toJSONString());
        internalServerError(ResponseUtil.generateFailed("Internal server error", 500).toJSONString());
    }

    private static boolean authenticated(Request request) {
        return request.headers("Authorization") != null && DBConnector.getInstance().checkAuthorized(request.headers("Authorization"));
    }
}
