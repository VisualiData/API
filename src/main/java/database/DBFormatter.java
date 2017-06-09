package database;

import com.mongodb.BasicDBList;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.simple.JSONObject;
import sensors.SensorsRoute;
import com.mongodb.BasicDBObject;
import org.json.simple.JSONArray;
import util.DBNames;
import util.DateTimeUtil;

import static util.DBNames.TIMESTAMP;
import static util.DBNames.TIME_FRAME;

public class DBFormatter {
    private static final Logger LOGGER = LogManager.getLogger(DBFormatter.class);

    public boolean formatDB(String timeFrame, String newTimeFrame){
        // for every type per sensor format data points
        try{
            SensorsRoute sensorRoute = new SensorsRoute();
            JSONArray allSensors = sensorRoute.getAllSensors();
            for (Object dbSensor : allSensors){
                BasicDBObject sensor = (BasicDBObject) dbSensor;
                BasicDBList types = (BasicDBList)sensor.get("types");
                for (Object type : types) {
                    formatCollection((String) sensor.get(DBNames.SENSOR_ID), timeFrame, newTimeFrame, (String) type);
                }
            }
            return true;
        }catch (Exception e){
            LOGGER.error(e);
            return false;
        }
    }

    private void formatCollection(String collectionName, String timeFrame, String newTimeFrame, String dataType){
        JSONArray period = getCollection(timeFrame,dataType,collectionName);
        Double total = 0.00;
        for (Object object : period) {
            try {
                BasicDBObject stamp = (BasicDBObject) object;
                total += (Double)stamp.get("value");
            } catch (Exception e){
                LOGGER.error(e);
            }
        }
        if (!period.isEmpty()) {
            BasicDBObject newDoc = new BasicDBObject();
            String currentDate = DateTimeUtil.getDate(timeFrame, true);
            newDoc.put(TIMESTAMP, currentDate);
            newDoc.put("value", total / period.size());
            newDoc.put(TIME_FRAME, newTimeFrame);
            DBQuery.insert(collectionName, newDoc);
        }
    }

    private JSONArray getCollection(String timeFrame, String dataType, String collectionName){
        BasicDBObject whereQuery = new BasicDBObject();
        BasicDBObject conditions = new BasicDBObject();
        whereQuery.put(TIME_FRAME, timeFrame);
        String currentDate = DateTimeUtil.getDate(timeFrame, true);
        String lastDate = DateTimeUtil.getDate(timeFrame, false);
        conditions.put("$lt",currentDate);
        conditions.put("$gt",lastDate);
        whereQuery.put(TIMESTAMP, conditions);
        whereQuery.put("type", dataType);
        return DBQuery.findQuery(collectionName,whereQuery, new BasicDBObject());
    }
}
