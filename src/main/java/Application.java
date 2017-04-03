import api.*;
import database.DBConnector;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import spark.Request;
import util.ResponseCodes;
import util.ResponseUtil;

import static spark.Spark.*;

/*
 * Main class for initializing API
 */
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
        return request.headers("Authorization") != null && DBConnector.getInstance().checkAuthorized(request.headers("Authorization"));
    }
}
