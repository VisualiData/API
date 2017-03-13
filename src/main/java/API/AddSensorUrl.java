package API;

import Sensors.SensorsRoute;

import static spark.Spark.post;

import org.json.JSONException;
import org.json.JSONObject;
import spark.Request;
import spark.Response;

/**
 * Created by Gebruiker on 13-3-2017.
 */
public class AddSensorUrl implements IURL {
    public void OpenUrl(){
        post("/sensor/:id", ( req, res) -> {
            if(req.contentType().equals("application/json")){
                return Interact(req, res);
            }
            System.out.println("not json");
            return "Send Json";
        });
    }
    public JSONObject Interact(Request req, Response res){
        SensorsRoute sensorRoute = new SensorsRoute();
        JSONObject requestBody = null;
        try {
            requestBody = new JSONObject(req.body());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return sensorRoute.AddSensor(requestBody);
    }
}
