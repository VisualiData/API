package Sensors;

import DataBase.DBConnector;
import org.json.JSONObject;

/**
 * Created by Gebruiker on 13-3-2017.
 */
public class SensorsRoute {
    DBConnector connector = DBConnector.getInstance();
    public JSONObject AddSensor (JSONObject reqBody) {
        // bouw die shit om
        return connector.Create("",reqBody);
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
