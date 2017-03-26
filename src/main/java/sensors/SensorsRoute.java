package sensors;

import database.DBConnector;
import com.mongodb.BasicDBObject;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

/**
 * Created by Gebruiker on 13-3-2017.
 */
public class SensorsRoute {
    private static final String SENSORDATA = "sensordata";
    DBConnector connector = DBConnector.getInstance();

    public JSONObject addSensor(BasicDBObject reqBody) {
        return connector.insert(SENSORDATA, reqBody);
    }
    public JSONObject updateSensor(BasicDBObject reqBody) {
        BasicDBObject find = new BasicDBObject();
        find.append("sensorId", reqBody.get("sensorId"));
        return connector.updateQuery(SENSORDATA, find, reqBody);
    }
    public JSONObject deleteSensor(BasicDBObject reqBody) {
        return connector.deleteQuery(SENSORDATA, reqBody);
    }
    public JSONArray getAllSensors(){
        return connector.getAllSensors();
    }
}
