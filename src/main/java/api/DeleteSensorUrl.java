package api;

import com.mongodb.BasicDBObject;
import com.mongodb.util.JSON;
import database.DBConnector;
import database.DBQuery;
import sensors.SensorsRoute;
import org.json.simple.JSONObject;
import spark.Request;
import spark.Response;
import util.DateTimeUtil;
import util.ResponseCodes;
import util.ResponseUtil;

import static spark.Spark.delete;

public class DeleteSensorUrl implements IURL {
    private String sensorId = null;

    /**
     * @api {delete} /sensor Archives a sensor
     * @apiHeader {String} Authorization Authorization key.
     * @apiName ArchiveSensor
     * @apiGroup Sensor
     * @apiVersion 1.0.0
     */
    @Override
    public void openUrl(){
        delete("/sensor", ( req, res) -> {
            if("application/json".equals(req.contentType())){
                if((boolean) interact(req, res).get("success")){
                    DBQuery.renameCollection(sensorId, sensorId + "_ARCHIVED_"+ DateTimeUtil.getTimeStamp());
                    return ResponseUtil.generateSuccessMessage("Sensor archived", ResponseCodes.SUCCESS);
                }else {
                    return ResponseUtil.generateFailed("Sensor not archived", 400);
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
        return sensorRoute.archiveSensor(requestBody);
    }
}
