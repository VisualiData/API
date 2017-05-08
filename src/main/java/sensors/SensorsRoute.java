package sensors;

import com.mongodb.BasicDBObject;
import database.DBQuery;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import util.DBNames;

public class SensorsRoute {
    public JSONObject addSensor(BasicDBObject reqBody) {
        return DBQuery.insert(DBNames.SENSOR_DATA, reqBody);
    }

    public JSONObject getSensor(String sensorId){
        BasicDBObject fields = new BasicDBObject("_id", 0);
        JSONArray result = DBQuery.find(DBNames.SENSOR_DATA, DBNames.SENSOR_ID, sensorId, fields);
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
        find.append(DBNames.SENSOR_ID, reqBody.get(DBNames.SENSOR_ID));
        return DBQuery.replaceQuery(DBNames.SENSOR_DATA, find, reqBody);
    }

    public JSONObject deleteSensor(BasicDBObject reqBody) {
        return DBQuery.deleteQuery(DBNames.SENSOR_DATA, reqBody);
    }

    public JSONObject archiveSensor(BasicDBObject reqBody){
        BasicDBObject archive = new BasicDBObject();
        archive.put("archived", true);
        return DBQuery.updateQuery(DBNames.SENSOR_DATA, reqBody, archive);
    }

    public JSONArray getAllSensors(){
        return DBQuery.getAllSensors();
    }
}
