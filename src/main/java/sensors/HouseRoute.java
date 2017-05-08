package sensors;

import com.mongodb.BasicDBObject;
import database.DBQuery;
import org.json.simple.JSONArray;
import util.DBNames;

public class HouseRoute {
    public JSONArray getAllSensors(BasicDBObject reqBody){
        return DBQuery.findQuery(DBNames.SENSOR_DATA, reqBody, new BasicDBObject("_id", 0));
    }
}
