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
    public void formatDB(){
        SensorsRoute sensorRoute = new SensorsRoute();
        JSONObject allSensors = sensorRoute.GetAllSensors();
    }

    private void  formatCollection(String collectionName){
        BasicDBObject whereQuery = new BasicDBObject();
        whereQuery.put("timeframe", "frame");
        DateFormat df=new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSZZ");
        df.setTimeZone(TimeZone.getTimeZone("UTC"));
        String currentDate = df.format(new Date(System.currentTimeMillis() - 3600 * 1000));
        whereQuery.put("timestamp", new BasicDBObject("$gt",currentDate));
        JSONArray period = connector.findQuery(collectionName,whereQuery);
        int total = 0;
        for (int i = 0; i < period.size(); i++) {
            JSONObject stamp = (JSONObject)period.get(i);
            total += (int)stamp.get("value");
        }
        BasicDBObject newDoc = new BasicDBObject();
        newDoc.put("timestamp",currentDate);
        newDoc.put("value",total/period.size());
        newDoc.put("timeframe","hour");
        connector.insert(newDoc,collectionName);
    }
}
