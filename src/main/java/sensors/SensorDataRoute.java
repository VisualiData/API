package sensors;

import database.DBConnector;
import com.mongodb.BasicDBObject;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public class SensorDataRoute {

    private DBConnector connector = DBConnector.getInstance();

    public JSONObject getSensorData(String SensorID) {
        // bouw die shit om
        JSONObject result = new JSONObject();
        JSONArray dbResult = connector.find("sensordata", "id", SensorID);
        result.put("data", dbResult);
        return result;
    }
    public JSONObject insertSensorDummyData(BasicDBObject reqBody) {
        return connector.insert(reqBody, reqBody.get("nodeName").toString());
    }
    public JSONObject insertSensorData(BasicDBObject reqBody) {
        return connector.insert(reqBody, reqBody.get("nodeName").toString());
    }
}
