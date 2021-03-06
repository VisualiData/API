package application;

import api.*;
import api.data.FormatDataBaseUrl;
import api.data.GetSensorDataUrl;
import api.house.GetHouseInfo;
import api.sensor.*;
import api.house.GetSensorsHouseUrl;
import database.DBConnector;
import database.DBQuery;
import database.DatabaseState;
import spark.Request;
import spark.Response;
import util.DBNames;
import util.HttpCodes;
import util.ResponseUtil;

import static spark.Spark.*;

/**
 * Main class for initializing API
 */
public class Application{
    private Application(){}

    public static void main (String[] args){
        // include static /public for api documentation
        staticFiles.location("/public");
        init();
        start();
    }

    private static void start(){
        // initialize DB connection
        DBConnector.initDB();
        // load url endpoints
        IURL[] addUrls = {
                new AddSensorUrl(),
                new CheckSensorStatus(),
                new GetSensorsHouseUrl(),
                new UpdateSensorUrl(),
                new DeleteSensorUrl(),
                new GetAllSensorsUrl(),
                new GetSensorDataUrl(),
                new PostSensorDataUrl(),
                new FormatDataBaseUrl(),
                new GetSensorURL(),
                new GetHouseInfo()
            };
        for (IURL url : addUrls){
            url.openUrl();
        }

        errorHandling();

        // Catch OPTIONS for javascript ajax calls
        options("/*", (Request request, Response response) -> "OK");

        before((request, response) -> {
            if(!"OPTIONS".equals(request.requestMethod())){
                if (DBConnector.getDBState() == DatabaseState.STATE_RUNNING) {
                    if (!authenticated(request)) {
                        halt(HttpCodes.NOT_AUTHORIZED, ResponseUtil.generateFailed("Not authorized", HttpCodes.NOT_AUTHORIZED).toJSONString());
                    }
                    // check if from header is present
                    if(request.headers("From") != null){
                        DBNames.setSensorData(request.headers("From"));
                    }else{
                        DBNames.setSensorData("");
                    }
                } else {
                    DBQuery.checkDBUp();
                    halt(HttpCodes.SERVER_ERROR, ResponseUtil.generateFailed("DB down", HttpCodes.SERVER_ERROR).toJSONString());
                }
            }
        });
        // add headers for response, Access-Control headers are for ajax calls
        after((request, response) -> {
            response.type("application/json");
            response.header("Access-Control-Allow-Origin", "*");
            response.header("Access-Control-Allow-Methods", "GET,HEAD,OPTIONS,POST,PUT, DELETE");
            response.header("Access-Control-Allow-Headers", "Origin, X-Requested-With, Content-Type, Accept, Authorization");
        });
    }

    // Custom error responses
    private static void errorHandling(){
        notFound(ResponseUtil.generateFailed("Not Found", HttpCodes.NOT_FOUND).toJSONString());
        internalServerError(ResponseUtil.generateFailed("Internal server error", HttpCodes.SERVER_ERROR).toJSONString());
    }

    // check if authorized in headers or url
    private static boolean authenticated(Request request) {
        if (request.headers("Authorization") != null) {
            return DBQuery.checkAuthorized(request.headers("Authorization"));
        } else
            return request.queryParams("authkey") != null && DBQuery.checkAuthorized(request.queryParams("authkey"));
    }
}
