package API;

import Sensors.SensorDataRoute;
import com.mongodb.BasicDBObject;
import com.mongodb.util.JSON;
import org.json.simple.JSONObject;
import spark.Request;
import spark.Response;

import static spark.Spark.post;

/**
 * Created by Gebruiker on 13-3-2017.
 */
public class PostSensorDataUrl implements IURL{
    public void OpenUrl(){
        post("/sensor", ( req, res) -> {
            if(req.contentType().equals("application/json")){
                return Interact(req, res);
            }
            System.out.println("not json");
            return "Send Json";
        });
    }
    public JSONObject Interact(Request req, Response res){
        SensorDataRoute sensorRoute = new SensorDataRoute();
        BasicDBObject requestBody = (BasicDBObject) JSON.parse(req.body());
        return sensorRoute.UpdateSensorData(requestBody);
    }
}
