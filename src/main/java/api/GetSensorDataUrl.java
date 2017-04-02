package api;

import org.json.simple.JSONArray;
import sensors.SensorDataRoute;
import org.json.simple.JSONObject;
import spark.Request;
import spark.Response;
import util.ResponseCodes;
import util.ResponseUtil;

import static spark.Spark.get;

public class GetSensorDataUrl implements IURL {
    /**
     * @api {get} /sensor/:id/:from/:to/:type Request Sensor data in time span
     * @apiName GetSensorData
     * @apiGroup Data
     *
     * @apiParam {String} id Sensor ID.
     * @apiParam {Date} from Start of time span.
     * @apiParam {Date} to End of time span
     * @apiParam {String} type Frame type.
     */
    @Override
    public void openUrl(){
        get("/sensor/:id/:from/:to/:type", this::interact);
    }

    @Override
    public JSONObject interact(Request req, Response res){
        SensorDataRoute sensorRoute = new SensorDataRoute();
        JSONArray result = sensorRoute.getSensorData(req.params(":id"),req.params(":from"),req.params(":to"),req.params(":type"));
        return ResponseUtil.generateSuccess(result, ResponseCodes.SUCCESS);
    }
}
