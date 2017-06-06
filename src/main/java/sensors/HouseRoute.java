package sensors;

import com.mongodb.BasicDBObject;
import database.DBQuery;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import util.DBNames;

public class HouseRoute {
    public JSONArray getAllSensors(BasicDBObject reqBody){
        return DBQuery.findQuery(DBNames.SENSOR_DATA, reqBody, new BasicDBObject("_id", 0));
    }

    public JSONObject getHouseInfo(BasicDBObject requestBody) {
        JSONArray result = DBQuery.findQuery(DBNames.HOUSE, requestBody, new BasicDBObject("_id", 0));
        if(result.size() == 1){
            BasicDBObject document = (BasicDBObject) result.get(0);
            JSONObject house = new JSONObject();
            house.putAll(document);
            return house;
        }
        return null;
    }
}
