package api;

import sensors.SensorsRoute;

import static spark.Spark.post;

import com.mongodb.BasicDBObject;
import com.mongodb.util.JSON;
import org.json.simple.JSONObject;
import spark.Request;
import spark.Response;
import util.ResponseCodes;
import util.ResponseUtil;

public class AddSensorUrl implements IURL {
    /**
     * @api {post} /sensor Add a sensor
     * @apiHeader {String} Authorization Authorization key.
     * @apiName AddSensor
     * @apiGroup Sensor
     * @apiVersion 1.0.0
     */
    @Override
    public void openUrl(){
        post("/sensor", (req, res) -> {
            if("application/json".equals(req.contentType())){
                if((boolean) interact(req, res).get("inserted")){
                    return ResponseUtil.generateSuccessMessage("Sensor added", ResponseCodes.CREATED);
                }else {
                    return ResponseUtil.generateFailed("Sensor not added", 400);
                }
            }
            return ResponseUtil.generateFailed("Send json format", 400);
        });
    }
    @Override
    public JSONObject interact(Request req, Response res){
        SensorsRoute sensorRoute = new SensorsRoute();
        BasicDBObject requestBody = (BasicDBObject) JSON.parse(req.body());
        return sensorRoute.addSensor(requestBody);
    }
}
