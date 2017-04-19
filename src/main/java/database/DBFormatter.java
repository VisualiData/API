package database;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.simple.JSONObject;
import sensors.SensorsRoute;
import com.mongodb.BasicDBObject;
import org.json.simple.JSONArray;
import util.DateTimeUtil;

public class DBFormatter {
    private static final Logger LOGGER = LogManager.getLogger(DBFormatter.class);
    public boolean formatDB(String timeframe, String newTimeframe){
        try{
            SensorsRoute sensorRoute = new SensorsRoute();
            JSONArray allSensors = sensorRoute.getAllSensors();
            for (Object collectionName : allSensors){
                JSONObject sensor = sensorRoute.getSensor((String)collectionName);
                JSONArray types = (JSONArray)sensor.get("types");
                for (Object type : types) {
                    formatCollection((String) collectionName, timeframe, newTimeframe, (String) type);
                }
            }
            return true;
        }catch (Exception e){
            LOGGER.error(e);
            return false;
        }
    }

    private void  formatCollection(String collectionName, String timeframe, String newTimeframe, String dataType){
        JSONArray period = getCollection(timeframe,dataType,collectionName);
        Double total = 0.00;
        for (Object object : period) {
            try {
                BasicDBObject stamp = (BasicDBObject) object;
                total += (Double)stamp.get("value");
            } catch (Exception e){
                LOGGER.error(e);
            }
        }
        BasicDBObject newDoc = new BasicDBObject();
        String currentDate = DateTimeUtil.getDate(timeframe, true);
        newDoc.put("timestamp",currentDate);
        newDoc.put("value",total/period.size());
        newDoc.put("timeframe",newTimeframe);
        DBQuery.insert(collectionName,newDoc);
    }

    private JSONArray getCollection(String timeframe, String dataType, String collectionName){
        BasicDBObject whereQuery = new BasicDBObject();
        BasicDBObject conditions = new BasicDBObject();
        whereQuery.put("timeframe", timeframe);
        String currentDate = DateTimeUtil.getDate(timeframe, true);
        String lastDate = DateTimeUtil.getDate(timeframe, false);
        conditions.put("$lt",currentDate);
        conditions.put("$gt",lastDate);
        whereQuery.put("timestamp", conditions);
        whereQuery.put("type", dataType);
        return DBQuery.findQuery(collectionName,whereQuery, new BasicDBObject());
    }
}
