package sensors;

import com.mongodb.BasicDBObject;
import database.DBQuery;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import util.DateTimeUtil;
import util.TimeFrameUtil;

import java.util.ArrayList;
import java.util.List;
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
        return DBQuery.insert(reqBody.get("nodeName").toString(), createDocument(reqBody));
    }
    public JSONObject insertSensorData(BasicDBObject reqBody) {
        return DBQuery.insert(reqBody.get("nodeName").toString(), createDocument(reqBody));
    }

    private BasicDBObject createDocument (BasicDBObject reqBody){
        List<BasicDBObject> documents = new ArrayList<>();
        JSONArray values = (JSONArray)reqBody.get("values");
        String id = (String)reqBody.get("sensor_id");
        for (Object value : values){
            BasicDBObject document = new BasicDBObject();
            document.put("nodeName",id);
            document.put("timeframe","frame");
            document.put("timestamp",DateTimeUtil.parseDateTime((int)((JSONObject)value).get("timestamp")));
            document.put("type",((JSONObject)value).get("type"));
            document.put("value", toDouble(((JSONObject)value).get("value")));
            documents.add(document);
        }
        return reqBody;
    }

    private Double toDouble(Object value) {
        try {
            return (double) value;
        } catch (ClassCastException e) {
            try {
                int val = (int) value;
                return (double) val;
            } catch (ClassCastException r) {
                try {
                    float val = (float) value;
                    return (double) val;
                } catch (ClassCastException s) {
                    LOGGER.debug("not supported format used while uploading data");
                    return null;
                }
            }
        }
    }
}
