package Sensors;

import DataBase.DBConnector;
import org.json.JSONObject;

/**
 * Created by Gebruiker on 13-3-2017.
 */
public class SensorDataRoute {
    DBConnector connector = DBConnector.getInstance();
    public JSONObject GetSensorData (String SensorID) {
        // bouw die shit om
        return connector.Read(SensorID);
    }
    public JSONObject UpdateSensorData (JSONObject reqBody) {
        // bouw die shit om
        return connector.Update("",reqBody);
    }
}
