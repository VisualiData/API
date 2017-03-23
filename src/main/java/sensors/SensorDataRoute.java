package sensors;

import database.DBConnector;
import com.mongodb.BasicDBObject;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import util.DateTimeUtil;

import java.util.concurrent.TimeUnit;

public class SensorDataRoute {

    private DBConnector connector = DBConnector.getInstance();

    public JSONObject getSensorData(String SensorID,String from,String to) {
        // bouw die shit om
        BasicDBObject whereQuery = new BasicDBObject();
        BasicDBObject conditions = new BasicDBObject();
        conditions.put("$lt",to);
        conditions.put("$gt",from);
        whereQuery.put("timestamp", conditions);
        long diffrence = DateTimeUtil.getDateDiff(from,to, TimeUnit.MILLISECONDS);
        if(diffrence <= 1000 * 3600 * 2){
            whereQuery.put("timeframe", "frame");
        }
        else if(diffrence > 1000 * 3600 * 2 && diffrence <= 1000 * 3600 * 10){
            whereQuery.put("timeframe", "quarter");
        }
        else if(diffrence > 1000 * 3600 * 10 && diffrence <= 1000 * 3600 * 24 * 7){
            whereQuery.put("timeframe", "hour");
        }
        else if(diffrence > 1000 * 3600 * 24 * 7 && diffrence <= 1000 * 3600 * 24 * 7 * 4){
            whereQuery.put("timeframe", "day");
        }
        else if(diffrence > 1000 * 3600 * 24 * 7 * 4){
            whereQuery.put("timeframe", "week");
        }
        JSONObject result = new JSONObject();
        JSONArray dbResult = connector.findQuery(SensorID,whereQuery);
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
