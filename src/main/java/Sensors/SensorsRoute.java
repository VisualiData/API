package Sensors;

import Database.DBConnector;
import com.mongodb.BasicDBObject;
import org.json.simple.JSONObject;

/**
 * Created by Gebruiker on 13-3-2017.
 */
public class SensorsRoute {
    DBConnector connector = DBConnector.getInstance();
    public JSONObject AddSensor (BasicDBObject reqBody) {
        JSONObject result = connector.insert(reqBody, "dev_table");
        return result;
    }
    public JSONObject UpdateSensor (JSONObject reqBody) {
        // bouw die shit om
        return connector.Update("",reqBody);
    }
    public JSONObject DeleteSensor (JSONObject reqBody) {
        // bouw die shit om
        return connector.Delete("");
    }
    public JSONObject GetAllSensors(JSONObject reqBody){
        return connector.Read("");
    }
}
