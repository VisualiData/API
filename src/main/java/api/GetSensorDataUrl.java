package api;

import sensors.SensorDataRoute;
import org.json.simple.JSONObject;
import spark.Request;
import spark.Response;

import static spark.Spark.get;

/**
 * Created by Gebruiker on 13-3-2017.
 */
public class GetSensorDataUrl implements IURL {
    public void OpenUrl(){
        get("/sensor/:id", ( req, res) -> {
            if(req.contentType().equals("application/json")){
                return Interact(req, res);
            }
            System.out.println("not json");
            return "Send Json";
        });
    }
    public JSONObject Interact(Request req, Response res){
        SensorDataRoute sensorRoute = new SensorDataRoute();
        return sensorRoute.getSensorData(req.params(":id"));
    }
}
