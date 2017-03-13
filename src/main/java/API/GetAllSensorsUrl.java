package API;

import Sensors.SensorDataRoute;
import Sensors.SensorsRoute;
import org.json.JSONObject;
import spark.Request;
import spark.Response;

import static spark.Spark.get;

/**
 * Created by Gebruiker on 13-3-2017.
 */
public class GetAllSensorsUrl implements IURL {
    public void OpenUrl(){
        get("/sensors", ( req, res) -> {
            if(req.contentType().equals("application/json")){
                return Interact(req, res);
            }
            System.out.println("not json");
            return "Send Json";
        });
    }
    public JSONObject Interact(Request req, Response res){
        SensorsRoute sensorRoute = new SensorsRoute();
        JSONObject requestBody =  new JSONObject(req.body());
        return sensorRoute.GetAllSensors(requestBody);
    }
}
