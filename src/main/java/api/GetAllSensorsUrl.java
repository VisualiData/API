package api;

import sensors.SensorsRoute;
import org.json.simple.JSONObject;
import spark.Request;
import spark.Response;
import util.ResponseUtil;

import static spark.Spark.get;

public class GetAllSensorsUrl implements IURL {
    @Override
    public void OpenUrl(){
        get("/sensors", this::Interact);
    }

    @Override
    public JSONObject Interact(Request req, Response res){
        SensorsRoute sensorRoute = new SensorsRoute();
        return ResponseUtil.generateSuccess(sensorRoute.getAllSensors());
    }
}
