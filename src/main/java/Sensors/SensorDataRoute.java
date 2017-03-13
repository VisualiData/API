package Sensors;

import Database.DBConnector;
import com.mongodb.BasicDBObject;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

/**
 * Created by Gebruiker on 13-3-2017.
 */
public class SensorDataRoute {
    private DBConnector connector = DBConnector.getInstance();
    public JSONObject GetSensorData (String SensorID) {
        // bouw die shit om
        JSONObject result = new JSONObject();
        JSONArray dbResult = connector.find("dev_table", "id", SensorID);
        result.put("data", dbResult);
        return result;
    }
    public JSONObject UpdateSensorData (BasicDBObject reqBody) {
        // bouw die shit om
        System.out.println(reqBody.toJson());
        connector.insert(reqBody, "sensordata");
//        return connector.Update("",new JSONObject());
        return new JSONObject();
    }
}
