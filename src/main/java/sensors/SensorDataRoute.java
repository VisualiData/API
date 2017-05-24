package sensors;

import com.mongodb.BasicDBList;
import com.mongodb.BasicDBObject;
import database.DBQuery;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import util.DateTimeUtil;
import util.TimeFrameUtil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static util.DBNames.SENSOR_ID;
import static util.DBNames.TIME_FRAME;
import static util.DBNames.TIMESTAMP;

public class SensorDataRoute {
    private static final Logger LOGGER = LogManager.getLogger(SensorDataRoute.class);
    private static final String VALUE = "value";

    @SuppressWarnings("unchecked")
    public JSONArray getSensorData(String sensorId, String from, String to, String dataType) {
        BasicDBObject whereQuery = new BasicDBObject();
        BasicDBObject conditions = new BasicDBObject();
        conditions.put("$lt",to);
        conditions.put("$gt",from);
        whereQuery.put(TIMESTAMP, conditions);

        // if sensor type is specified use them to select te data
        if(dataType != null) {
            String[] splitDataTypes = dataType.split(",");
            if (splitDataTypes.length == 1) {
                whereQuery.put("type", dataType);
            }else{
                JSONArray dataTypes = new JSONArray();
                dataTypes.addAll(Arrays.asList(splitDataTypes));
                whereQuery.put("type", new BasicDBObject("$in", dataTypes));
            }
        }
        long difference = DateTimeUtil.getDateDiff(from,to, TimeUnit.MILLISECONDS);
        LOGGER.debug(whereQuery.toString());
        whereQuery.put(TIME_FRAME, TimeFrameUtil.getTimeFrame(difference));
        BasicDBObject fields = new BasicDBObject();
        fields.put("_id", 0);
        fields.put(VALUE, 1);
        fields.put(TIMESTAMP, 1);
        fields.put("type", 1);
        return DBQuery.findQuery(sensorId,whereQuery,fields);
    }

    public JSONObject insertSensorDummyData(BasicDBObject reqBody) {
        return DBQuery.insertMany(reqBody.get(SENSOR_ID).toString(), processDummyDocuments(reqBody));
    }
    public JSONObject insertSensorData(BasicDBObject reqBody) {
        return DBQuery.insertMany(reqBody.get(SENSOR_ID).toString(), createDocument(reqBody));
    }

    private List<BasicDBObject> createDocument (BasicDBObject reqBody){
        List<BasicDBObject> documents = new ArrayList<>();
        BasicDBList values = (BasicDBList)reqBody.get("values");
        String id = (String)reqBody.get(SENSOR_ID);
        for (Object value : values) {
            BasicDBObject document = new BasicDBObject();
            document.put("id", id);
            document.put(TIME_FRAME, "frame");
            document.put(TIMESTAMP, DateTimeUtil.parseDateTime((long) ((BasicDBObject) value).get(TIMESTAMP)));
            document.put("type", ((BasicDBObject) value).get("type"));
            document.put(VALUE, toDouble(((BasicDBObject) value).get(VALUE)));
            documents.add(document);
        }
        return documents;
    }

    private List<BasicDBObject> processDummyDocuments(BasicDBObject reqBody){
        List<BasicDBObject> documents = new ArrayList<>();
        BasicDBList values = (BasicDBList)reqBody.get("values");
        String id = (String)reqBody.get(SENSOR_ID);
        for (Object value : values) {
            BasicDBObject document = new BasicDBObject();
            document.put("id", id);
            document.put(TIME_FRAME, ((BasicDBObject) value).get(TIME_FRAME));
            document.put(TIMESTAMP, DateTimeUtil.parseDateTime((long) ((BasicDBObject) value).get(TIMESTAMP)));
            document.put("type", ((BasicDBObject) value).get("type"));
            document.put(VALUE, toDouble(((BasicDBObject) value).get(VALUE)));
            documents.add(document);
        }
        return documents;
    }

    private Double toDouble(Object value) {
        try {
            LOGGER.debug("try double");
            return (double) value;
        } catch (ClassCastException e) {
            LOGGER.debug(e);
            try {
                LOGGER.debug("try int");
                int val = (int) value;
                return (double) val;
            } catch (ClassCastException r) {
                LOGGER.debug(r);
                try {
                    LOGGER.debug("try float");
                    float val = (float) value;
                    return (double) val;
                } catch (ClassCastException s) {
                    LOGGER.debug(s);
                    LOGGER.debug("not supported format used while uploading data");
                    return 0.0;
                }
            }
        }
    }
}
