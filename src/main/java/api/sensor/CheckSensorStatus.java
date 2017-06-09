package api.sensor;

import api.IURL;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import sensors.CheckSensorRoute;
import spark.Request;
import spark.Response;
import util.HttpCodes;
import util.ResponseUtil;

import static spark.Spark.get;

public class CheckSensorStatus implements IURL{
    /**
     * @api {post} /sensor/check-status Updates sensor status in database
     * @apiHeader {String} Authorization Authorization key.
     * @apiName CheckSensorStatus
     * @apiGroup Sensor
     * @apiVersion 1.0.0
     */
    @Override
    public void openUrl() {
        get("/sensor/check-status", this::interact);
    }

    @Override
    public JSONObject interact(Request req, Response res) {
        new CheckSensorRoute().checkSensorStatus();
        return ResponseUtil.generateSuccess(new JSONArray(), HttpCodes.SUCCESS);
    }
}
