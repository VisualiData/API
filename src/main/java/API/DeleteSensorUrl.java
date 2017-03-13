package API;

import Sensors.SensorsRoute;
import org.json.simple.JSONObject;
import spark.Request;
import spark.Response;

import static spark.Spark.delete;

/**
 * Created by Gebruiker on 13-3-2017.
 */
public class DeleteSensorUrl implements IURL {
    public void OpenUrl(){
        delete("/addsensor", ( req, res) -> {
            if(req.contentType().equals("application/json")){
                return Interact(req, res);
            }
            System.out.println("not json");
            return "Send Json";
        });
    }
    public JSONObject Interact(Request req, Response res){
        SensorsRoute sensorRoute = new SensorsRoute();
        JSONObject requestBody = new JSONObject();
        return sensorRoute.DeleteSensor(requestBody);
    }
}
