package sensors;

import database.DBConnector;
import com.mongodb.BasicDBObject;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

/**
 * Created by Gebruiker on 13-3-2017.
 */
public class SensorsRoute {
    private static final String SENSOR_DATA = "sensordata";
    private DBConnector connector = DBConnector.getInstance();

    public JSONObject addSensor(BasicDBObject reqBody) {
        return connector.insert(SENSOR_DATA, reqBody);
    }

    public JSONObject getSensor(String sensorId){
        BasicDBObject fields = new BasicDBObject("_id", 0);
        JSONArray result = connector.find("sensordata", "sensorId", sensorId, fields);
        if(!result.isEmpty()){
            BasicDBObject document = (BasicDBObject) result.get(0);
            JSONObject sensor = new JSONObject();
            sensor.putAll(document);
            return sensor;
        }else{
            return null;
        }
    }

    public JSONObject updateSensor(BasicDBObject reqBody) {
        BasicDBObject find = new BasicDBObject();
        find.append("sensorId", reqBody.get("sensorId"));
        return connector.updateQuery(SENSOR_DATA, find, reqBody);
    }

    public JSONObject deleteSensor(BasicDBObject reqBody) {
        return connector.deleteQuery(SENSOR_DATA, reqBody);
    }

    public JSONArray getAllSensors(){
        return connector.getAllSensors();
    }
}
