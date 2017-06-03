package api.sensor;

import api.IURL;
import org.json.simple.JSONObject;
import sensors.SensorsRoute;
import spark.Request;
import spark.Response;
import util.HttpCodes;
import util.ResponseUtil;

import static spark.Spark.get;

public class GetSensorURL implements IURL {
    /**
     * @api {get} /sensor/:id Request Sensor information
     * @apiHeader {String} Authorization Authorization key.
     * @apiName GetSensor
     * @apiGroup Sensor
     * @apiExample {js} Example url:
     *     https://api.visualidata.nl/sensor/CHIBB-Test-02
     * @apiParam {String} id Sensor ID.
     * @apiSuccessExample {json} Success-Response:
     * HTTP/1.1 200 OK
     *     {"statuscode":200,"data":{"sensor_id":"CHIBB-Test-02","nodeName":"","position":{"x":"1179","y":"418","floor":"1","house":"CHIBB"},"type":"Temperature","nodeType":"","status":"active"},"message":"","status":"success"}
     * @apiVersion 1.0.0
     */
    @Override
    public void openUrl() {get("/sensor/:id", this::interact);}

    @Override
    public JSONObject interact(Request req, Response res) {
        SensorsRoute sensorsRoute = new SensorsRoute();
        JSONObject result = sensorsRoute.getSensor(req.params("id"));
        if (result != null) {
            return ResponseUtil.generateSuccess(result, HttpCodes.SUCCESS);
        }else{
            return ResponseUtil.generateFailed("Sensor not found", HttpCodes.NOT_FOUND);
        }
    }
}
