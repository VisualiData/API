package api;

import sensors.SensorsRoute;
import org.json.simple.JSONObject;
import spark.Request;
import spark.Response;
import util.ResponseCodes;
import util.ResponseUtil;

import static spark.Spark.get;

public class GetAllSensorsUrl implements IURL {
    /**
     * @api {get} /sensors Request All Sensors
     * @apiName GetAllSensors
     * @apiGroup Sensor
     */
    @Override
    public void openUrl(){
        get("/sensors", this::interact);
    }

    @Override
    public JSONObject interact(Request req, Response res){
        SensorsRoute sensorRoute = new SensorsRoute();
        return ResponseUtil.generateSuccess(sensorRoute.getAllSensors(), ResponseCodes.SUCCESS);
    }
}
