package api;

import com.mongodb.BasicDBObject;
import com.mongodb.util.JSON;
import database.DBConnector;
import javafx.beans.binding.StringBinding;
import sensors.SensorsRoute;
import org.json.simple.JSONObject;
import spark.Request;
import spark.Response;
import util.DateTimeUtil;
import util.ResponseCodes;
import util.ResponseUtil;

import static spark.Spark.delete;

/**
 * Created by Gebruiker on 13-3-2017.
 */
public class DeleteSensorUrl implements IURL {
    private String sensorId = null;

    @Override
    public void openUrl(){
        delete("/sensor", ( req, res) -> {
            if("application/json".equals(req.contentType())){
                if((boolean) interact(req, res).get("success")){
                    DBConnector.renameCollection(sensorId, sensorId + "_ARCHIVED_"+ DateTimeUtil.getTimeStamp());
                    return ResponseUtil.generateSuccessMessage("Sensor deleted", ResponseCodes.SUCCESS);
                }else {
                    return ResponseUtil.generateFailed("Sensor not deleted", 400);
                }
            }
            return ResponseUtil.generateFailed("Send json format", 400);
        });
    }

    @Override
    public JSONObject interact(Request req, Response res){
        SensorsRoute sensorRoute = new SensorsRoute();
        BasicDBObject requestBody = (BasicDBObject) JSON.parse(req.body());
        sensorId = (String) requestBody.get("sensorId");
        return sensorRoute.deleteSensor(requestBody);
    }
}
