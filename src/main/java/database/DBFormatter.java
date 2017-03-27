package database;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import sensors.SensorsRoute;
import com.mongodb.BasicDBObject;
import org.json.simple.JSONArray;
import util.DateTimeUtil;

/**
 * Created by Gebruiker on 20-3-2017.
 */
public class DBFormatter {
    private static final Logger LOGGER = LogManager.getLogger(DBFormatter.class);
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
        BasicDBObject conditions = new BasicDBObject();
        whereQuery.put("timeframe", timeframe);
        String currentDate = DateTimeUtil.getDate(timeframe, true);
        String lastDate = DateTimeUtil.getDate(timeframe, false);
        conditions.put("$lt",currentDate);
        conditions.put("$gt",lastDate);
        whereQuery.put("timestamp", conditions);
        JSONArray period = connector.findQuery(collectionName,whereQuery, new BasicDBObject());
        LOGGER.debug(whereQuery.toString());
        LOGGER.debug(period.size());

        Double total = 0.00;
        for (Object object : period) {
            try {
                BasicDBObject stamp = (BasicDBObject) object;
                total += (Double)stamp.get("value");
            } catch (Exception e){
                LOGGER.error(e);
            }
        }
        LOGGER.debug(total+"");
        BasicDBObject newDoc = new BasicDBObject();
        newDoc.put("timestamp",currentDate);
        newDoc.put("value",total/period.size());
        newDoc.put("timeframe",newTimeframe);
        connector.insert(collectionName,newDoc);
    }
}
