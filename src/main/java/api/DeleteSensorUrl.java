package api;

import com.mongodb.BasicDBObject;
import com.mongodb.util.JSON;
import sensors.SensorsRoute;
import org.json.simple.JSONObject;
import spark.Request;
import spark.Response;
import util.ResponseUtil;

import static spark.Spark.delete;

/**
 * Created by Gebruiker on 13-3-2017.
 */
public class DeleteSensorUrl implements IURL {
    public void OpenUrl(){
        delete("/sensor", ( req, res) -> {
            if("application/json".equals(req.contentType())){
                if((boolean) Interact(req, res).get("success")){
                    return ResponseUtil.generateSuccessMessage("Sensor deleted");
                }else {
                    return ResponseUtil.generateFailed("Sensor not deleted", 400);
                }
            }
            return ResponseUtil.generateFailed("Send json format", 400);
        });
    }
    public JSONObject Interact(Request req, Response res){
        SensorsRoute sensorRoute = new SensorsRoute();
        BasicDBObject requestBody = (BasicDBObject) JSON.parse(req.body());
        return sensorRoute.deleteSensor(requestBody);
    }
}
