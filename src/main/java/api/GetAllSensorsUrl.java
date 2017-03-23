package api;

import sensors.SensorsRoute;
import org.json.simple.JSONObject;
import spark.Request;
import spark.Response;
import util.DateTimeUtil;
import util.ResponseUtil;

import static spark.Spark.get;

/**
 * Created by Gebruiker on 13-3-2017.
 */
public class GetAllSensorsUrl implements IURL {
    public void OpenUrl(){
        get("/sensors", (req, res) -> {
            return Interact(req, res);
        });
    }
    public JSONObject Interact(Request req, Response res){
        SensorsRoute sensorRoute = new SensorsRoute();
        JSONObject requestBody = new JSONObject();
        return ResponseUtil.generateSuccess(sensorRoute.getAllSensors());
    }
}
