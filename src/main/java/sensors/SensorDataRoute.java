package sensors;

import com.mongodb.BasicDBObject;
import database.DBQuery;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import util.DateTimeUtil;
import util.TimeFrameUtil;

import java.util.concurrent.TimeUnit;

public class SensorDataRoute {
    private static final Logger LOGGER = LogManager.getLogger(SensorDataRoute.class);

    public JSONArray getSensorData(String sensorId,String from,String to, String dataType) {
        // bouw die shit om
        BasicDBObject whereQuery = new BasicDBObject();
        BasicDBObject conditions = new BasicDBObject();
        conditions.put("$lt",to);
        conditions.put("$gt",from);
        whereQuery.put("timestamp", conditions);
        whereQuery.put("type", dataType);
        long difference = DateTimeUtil.getDateDiff(from,to, TimeUnit.MILLISECONDS);
        LOGGER.debug(whereQuery.toString());
        whereQuery.put("timeframe", TimeFrameUtil.getTimeFrame(difference));
        BasicDBObject fields = new BasicDBObject();
        fields.put("_id", 0);
        fields.put("value", 1);
        fields.put("timestamp", 1);
        return DBQuery.findQuery(sensorId,whereQuery,fields);
    }

    public JSONObject insertSensorDummyData(BasicDBObject reqBody) {
        return DBQuery.insert(reqBody.get("nodeName").toString(), reqBody);
    }
    public JSONObject insertSensorData(BasicDBObject reqBody) {
        return DBQuery.insert(reqBody.get("nodeName").toString(), reqBody);
    }
}
