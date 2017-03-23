package database;

import sensors.SensorsRoute;
import com.mongodb.BasicDBObject;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import util.DateTimeUtil;
import util.ResponseUtil;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

/**
 * Created by Gebruiker on 20-3-2017.
 */
public class DBFormatter {
    private DBConnector connector = DBConnector.getInstance();
    public boolean formatDB(String timeframe, String newTimeframe){
        try{
            SensorsRoute sensorRoute = new SensorsRoute();
            JSONArray allSensors = sensorRoute.getAllSensors();
            for (Object collectionName : allSensors){
                formatCollection((String)collectionName, timeframe, newTimeframe);
            }
            return true;
        }catch (Exception e){
            return false;
        }
    }

    private void  formatCollection(String collectionName, String timeframe, String newTimeframe){
        BasicDBObject whereQuery = new BasicDBObject();
        whereQuery.put("timeframe", timeframe);
        String currentDate = DateTimeUtil.GetDate(timeframe, true);
        String lastDate = DateTimeUtil.GetDate(newTimeframe, false);
        whereQuery.put("timestamp", new BasicDBObject("$lt",currentDate));
        whereQuery.put("timestamp", new BasicDBObject("$gt",lastDate));
        JSONArray period = connector.findQuery(collectionName,whereQuery);
        int total = 0;
        for (Object object : period) {
            JSONObject stamp = (JSONObject)object;
            total += (int)stamp.get("value");
        }
        BasicDBObject newDoc = new BasicDBObject();
        newDoc.put("timestamp",currentDate);
        newDoc.put("value",total/period.size());
        newDoc.put("timeframe",newTimeframe);
        connector.insert(newDoc,collectionName);
    }
}
