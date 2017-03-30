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

/**
 * Created by Gebruiker on 13-3-2017.
 */
public class AddSensorUrl implements IURL {
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
