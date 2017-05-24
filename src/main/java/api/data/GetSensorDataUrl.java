package api.data;

import api.IURL;
import org.json.simple.JSONArray;
import sensors.SensorDataRoute;
import org.json.simple.JSONObject;
import spark.Request;
import spark.Response;
import util.HttpCodes;
import util.ResponseUtil;

import static spark.Spark.get;

public class GetSensorDataUrl implements IURL {
    /**
     * @api {get} /sensor/:id/:from/:to Request Sensor data in time span
     * @apiHeader {String} Authorization Authorization key.
     * @apiName GetSensorDataAll
     * @apiGroup Data
     *
     * @apiParam {String} id Sensor ID.
     * @apiParam {Date} from Start of time span.
     * @apiParam {Date} to End of time span
     * @apiVersion 1.0.0
     */
    /**
     * @api {get} /sensor/:id/:from/:to/:type Request Sensor data in time span for given type(s)
     * @apiHeader {String} Authorization Authorization key.
     * @apiName GetSensorData
     * @apiGroup Data
     *
     * @apiParam {String} id Sensor ID.
     * @apiParam {Date} from Start of time span.
     * @apiParam {Date} to End of time span
     * @apiParam {String} date type e.g. Temperature OR Temperature,Pressure
     * @apiVersion 1.0.0
     */
    @Override
    public void openUrl(){
        get("/sensor/:id/:from/:to", this::interact);
        get("/sensor/:id/:from/:to/:type", this::interact);
    }

    @Override
    public JSONObject interact(Request req, Response res){
        SensorDataRoute sensorRoute = new SensorDataRoute();
        JSONArray result = sensorRoute.getSensorData(req.params(":id"),req.params(":from"),req.params(":to"),req.params(":type"));
        return ResponseUtil.generateSuccess(result, HttpCodes.SUCCESS);
    }
}
