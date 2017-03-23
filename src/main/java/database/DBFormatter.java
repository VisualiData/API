package database;

import Sensors.SensorsRoute;
import com.mongodb.BasicDBObject;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

/**
 * Created by Gebruiker on 20-3-2017.
 */
public class DBFormatter {
    private DBConnector connector = DBConnector.getInstance();
    public void formatDB(String timeframe, String newTimeframe){
        SensorsRoute sensorRoute = new SensorsRoute();
        JSONObject allSensors = sensorRoute.GetAllSensors();
        formatCollection("name", timeframe, newTimeframe);
    }

    private void  formatCollection(String collectionName, String timeframe, String newTimeframe){
        BasicDBObject whereQuery = new BasicDBObject();
        whereQuery.put("timeframe", timeframe);
        String currentDate = GetDate(timeframe, true);
        String lastDate = GetDate(newTimeframe, false);
        whereQuery.put("timestamp", new BasicDBObject("$lt",currentDate));
        whereQuery.put("timestamp", new BasicDBObject("$gt",lastDate));
        JSONArray period = connector.findQuery(collectionName,whereQuery);
        int total = 0;
        for (int i = 0; i < period.size(); i++) {
            JSONObject stamp = (JSONObject)period.get(i);
            total += (int)stamp.get("value");
        }
        BasicDBObject newDoc = new BasicDBObject();
        newDoc.put("timestamp",currentDate);
        newDoc.put("value",total/period.size());
        newDoc.put("timeframe",newTimeframe);
        connector.insert(newDoc,collectionName);
    }

    private String GetDate(String timeframe, boolean current){
        String format = "yyyy-MM-dd'T'00:00:00.00ZZ";
        int HoursAdjustment = 0;
        if (timeframe == "frame") {
            format = "yyyy-MM-dd'T'HH:mm:ss.SSZZ";
        }
        if(!current){
            switch (timeframe){
                case "hour":
                    HoursAdjustment = 1;
                    break;
                case "day":
                    HoursAdjustment = 24;
                    break;
                case "week" :
                    HoursAdjustment = 168;
                    break;
            }
        }
        DateFormat df=new SimpleDateFormat(format);
        df.setTimeZone(TimeZone.getTimeZone("UTC"));
        return df.format(new Date(System.currentTimeMillis() - 3600 * 1000 * HoursAdjustment));
    }
}
