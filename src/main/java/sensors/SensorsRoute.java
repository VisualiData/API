package sensors;

import database.DBConnector;
import com.mongodb.BasicDBObject;
import database.DBQuery;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public class SensorsRoute {
    private static final String IDENTIFIER = "sensorId";
    private static final String SENSOR_DATA = "sensordata";
    private DBConnector connector = DBConnector.getInstance();

    public JSONObject addSensor(BasicDBObject reqBody) {
        return DBQuery.insert(SENSOR_DATA, reqBody);
    }

    public JSONObject getSensor(String sensorId){
        BasicDBObject fields = new BasicDBObject("_id", 0);
        JSONArray result = DBQuery.find("sensordata", IDENTIFIER, sensorId, fields);
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
        find.append(IDENTIFIER, reqBody.get(IDENTIFIER));
        return DBQuery.updateQuery(SENSOR_DATA, find, reqBody);
    }

    public JSONObject deleteSensor(BasicDBObject reqBody) {
        return DBQuery.deleteQuery(SENSOR_DATA, reqBody);
    }

    public JSONObject archiveSensor(BasicDBObject reqBody){
        BasicDBObject archive = new BasicDBObject();
        archive.put("archived", true);
        return DBQuery.updateQuery(SENSOR_DATA, reqBody, archive);
    }

    public JSONArray getAllSensors(){
        return DBQuery.getAllSensors();
    }
}
