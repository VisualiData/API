package api;

import sensors.SensorsRoute;

import static spark.Spark.post;

import com.mongodb.BasicDBObject;
import com.mongodb.util.JSON;
import org.json.simple.JSONObject;
import spark.Request;
import spark.Response;
import util.ResponseUtil;

public class UpdateSensorUrl implements IURL {
    @Override
    public void OpenUrl(){
        // TODO check if sensorId is present
        post("/sensor/update", (req, res) -> {
            if("application/json".equals(req.contentType())){
                if((boolean) Interact(req, res).get("success")){
                    return ResponseUtil.generateSuccessMessage("Sensor updated");
                }else {
                    return ResponseUtil.generateFailed("Sensor not updated", 400);
                }
            }
            return ResponseUtil.generateFailed("Send json format", 400);
        });
    }
    @Override
    public JSONObject Interact(Request req, Response res){
        SensorsRoute sensorRoute = new SensorsRoute();
        BasicDBObject requestBody = (BasicDBObject) JSON.parse(req.body());
        return sensorRoute.updateSensor(requestBody);
    }
}
