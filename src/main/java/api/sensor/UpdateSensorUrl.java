package api.sensor;

import api.IURL;
import com.mongodb.BasicDBObject;
import com.mongodb.util.JSON;
import org.json.simple.JSONObject;
import sensors.SensorsRoute;
import spark.Request;
import spark.Response;
import util.HttpCodes;
import util.ResponseUtil;

import static spark.Spark.post;

public class UpdateSensorUrl implements IURL {
    /**
     * @api {post} /sensor/update Update a sensor
     * @apiHeader {String} Authorization Authorization key.
     * @apiName UpdateSensor
     * @apiGroup Sensor
     * @apiVersion 1.0.0
     */
    @Override
    public void openUrl(){
        post("/sensor/update", (req, res) -> {
            if("application/json".equals(req.contentType())){
                if((boolean) interact(req, res).get("updated")){
                    return ResponseUtil.generateSuccessMessage("Sensor updated", HttpCodes.SUCCESS);
                }else {
                    return ResponseUtil.generateFailed("Sensor not updated", 400);
                }
            }
            return ResponseUtil.generateFailed("Send json format", 400);
        });
    }
    @Override
    public JSONObject interact(Request req, Response res){
        SensorsRoute sensorRoute = new SensorsRoute();
        BasicDBObject requestBody = (BasicDBObject) JSON.parse(req.body());
        return sensorRoute.updateSensor(requestBody);
    }
}
