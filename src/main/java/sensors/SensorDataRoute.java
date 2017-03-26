package sensors;

import database.DBConnector;
import com.mongodb.BasicDBObject;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import util.DateTimeUtil;

import java.util.concurrent.TimeUnit;

public class SensorDataRoute {
    private static final Logger LOGGER = LogManager.getLogger(SensorDataRoute.class);
    private DBConnector connector = DBConnector.getInstance();

    public JSONArray getSensorData(String sensorId,String from,String to) {
        // bouw die shit om
        BasicDBObject whereQuery = new BasicDBObject();
        BasicDBObject conditions = new BasicDBObject();
        conditions.put("$lt",to);
        conditions.put("$gt",from);
        whereQuery.put("timestamp", conditions);
        long diffrence = DateTimeUtil.getDateDiff(from,to, TimeUnit.MILLISECONDS);
        // less or equal than 2 hours
        if(diffrence <= 1000 * 3600 * 2){
            LOGGER.debug("frame");
            whereQuery.put("timeframe", "frame");
        }
        // between 2 and 10 hours
        else if(diffrence > 1000 * 3600 * 2 && diffrence <= 1000 * 3600 * 10){
            LOGGER.debug("quarter");
            whereQuery.put("timeframe", "quarter");
        }
        // between 10 hours and 7 days
        else if(diffrence > 1000 * 3600 * 10 && diffrence <= 1000 * 3600 * 24 * 7){
            LOGGER.debug("hour");
            whereQuery.put("timeframe", "hour");
        }
        // between a week and 4 weeks
        else if(diffrence > 1000 * 3600 * 24 * 7 && diffrence <= 1000 * 3600 * 24 * 7 * 4){
            LOGGER.debug("day");
            whereQuery.put("timeframe", "day");
        }
        // more than 4 weeks
        else if(diffrence > 1000 * 3600 * 24 * 7 * 4){
            LOGGER.debug("week");
            whereQuery.put("timeframe", "week");
        }
        LOGGER.debug(whereQuery.toString());

        BasicDBObject fields = new BasicDBObject();
        fields.put("_id", 0);
        fields.put("value", 1);
        fields.put("timestamp", 1);
        return connector.findQuery(sensorId,whereQuery,fields);
    }
    public JSONObject insertSensorDummyData(BasicDBObject reqBody) {
        return connector.insert(reqBody.get("nodeName").toString(), reqBody);
    }
    public JSONObject insertSensorData(BasicDBObject reqBody) {
        return connector.insert(reqBody.get("nodeName").toString(), reqBody);
    }
}
