package application;

import api.*;
import database.DBConnector;
import database.DBQuery;
import database.DatabaseState;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import spark.Request;
import util.ResponseCodes;
import util.ResponseUtil;

import static spark.Spark.*;

/**
 * Main class for initializing API
 */
public class Application{
    private static final Logger LOGGER = LogManager.getLogger(Application.class);
    private Application(){}

    public static void main (String[] args){
        // include static /public for api documentation
        staticFiles.location("/public");
        init();
        start();
    }

    private static void start(){
        DBConnector.initDB();
        IURL[] addUrls = {
                new AddSensorUrl(),
                new GetSensorsHouseUrl(),
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
        options("/*", (request, response) -> {

            String accessControlRequestHeaders = request.headers("Access-Control-Request-Headers");
            if (accessControlRequestHeaders != null) {
                response.header("Access-Control-Allow-Headers", accessControlRequestHeaders);
            }

            String accessControlRequestMethod = request.headers("Access-Control-Request-Method");
            if (accessControlRequestMethod != null) {
                response.header("Access-Control-Allow-Methods", accessControlRequestMethod);
            }

            return "OK";
        });
        before((request, response) -> {
            LOGGER.debug(request.requestMethod() + " request made to: " + request.uri());
            LOGGER.debug(request.body());
            if(DBConnector.getDBState() == DatabaseState.STATE_RUNNING){
                System.out.println("request made");
//                if ()
//                if(!authenticated(request) && !("/sensordata/dummy".equals(request.uri()) || "/sensordata".equals(request.uri())) ){
//                    response.type("application/json");
//                    halt(ResponseCodes.NOT_AUTHORIZED, ResponseUtil.generateFailed("Not authorized", ResponseCodes.NOT_AUTHORIZED).toJSONString());
//                }
            } else {
                DBQuery.checkDBUp();
                halt(ResponseCodes.SERVER_ERROR, ResponseUtil.generateFailed("DB down", ResponseCodes.SERVER_ERROR).toJSONString());
            }
        });
        after((request, response) -> {
            response.type("application/json");
            response.header("Access-Control-Allow-Origin", "*");
            response.header("Access-Control-Allow-Methods", "GET,HEAD,OPTIONS,POST,PUT, DELETE");
            response.header("Access-Control-Allow-Headers", "Origin, X-Requested-With, Content-Type, Accept, Authorization");
        });
    }


    private static void errorHandling(){
        notFound(ResponseUtil.generateFailed("Not Found", ResponseCodes.NOT_FOUND).toJSONString());
        internalServerError(ResponseUtil.generateFailed("Internal server error", ResponseCodes.SERVER_ERROR).toJSONString());
    }

    private static boolean authenticated(Request request) {
        return request.headers("Authorization") != null && DBQuery.checkAuthorized(request.headers("Authorization"));
    }
}
