package api;

import org.json.simple.JSONArray;
import sensors.SensorDataRoute;
import org.json.simple.JSONObject;
import spark.Request;
import spark.Response;
import util.ResponseUtil;

import static spark.Spark.get;

/**
 * Created by Gebruiker on 13-3-2017.
 */
public class GetSensorDataUrl implements IURL {
    @Override
    public void openUrl(){
        get("/sensor/:id/:from/:to", this::interact);
    }

    @Override
    public JSONObject interact(Request req, Response res){
        SensorDataRoute sensorRoute = new SensorDataRoute();
        JSONArray result = sensorRoute.getSensorData(req.params(":id"),req.params(":from"),req.params(":to"));
        return ResponseUtil.generateSuccess(result);
    }
}
