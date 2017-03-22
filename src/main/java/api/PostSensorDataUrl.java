package api;

import sensors.SensorDataRoute;
import com.mongodb.BasicDBObject;
import com.mongodb.util.JSON;
import org.json.simple.JSONObject;
import spark.Request;
import spark.Response;
import util.ResponseUtil;

import static spark.Spark.post;

/**
 * Passes data to DB class and returns OK status
 */
public class PostSensorDataUrl implements IURL{
    @Override
    public void OpenUrl(){
        post("/sensordata", ( req, res) -> {
            if("application/json".equals(req.contentType())){
                Interact(req, res);
                return ResponseUtil.generateSuccessMessage("Data added");
            }
            return ResponseUtil.generateFailed("Send json format", 400);
        });

        // this route is for dummy data, which includes correct timestamp!
        post("/sensordata/dummy", (req,res) ->{
            if("application/json".equals(req.contentType())){
                Interact(req, res);
                return ResponseUtil.generateSuccessMessage("Data added");
            }
            return ResponseUtil.generateFailed("Send json format", 400);
        });
    }
    @Override
    public JSONObject Interact(Request req, Response res){
        SensorDataRoute sensorDataRoute = new SensorDataRoute();
        BasicDBObject requestBody = (BasicDBObject) JSON.parse(req.body());
        return sensorDataRoute.insertSensorData(requestBody);
    }
}
