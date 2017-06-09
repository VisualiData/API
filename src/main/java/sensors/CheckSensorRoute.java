package sensors;

import com.mongodb.BasicDBObject;
import database.DBQuery;
import org.json.simple.JSONArray;
import util.DBNames;
import util.DateTimeUtil;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;
import java.util.concurrent.TimeUnit;

@SuppressWarnings("unchecked")
public class CheckSensorRoute {
    private static final String TIMESTAMP_FORMAT = "yyyy-MM-dd'T'HH:mm:ss";

    public void checkSensorStatus(){
        JSONArray sensors = new SensorsRoute().getAllSensors();
        sensors.forEach((Object value) -> {
            BasicDBObject document = (BasicDBObject) value;
            String sensorId = (String) document.get(DBNames.SENSOR_ID);
            calculateInterval(sensorId);
        });
    }

    private void calculateInterval(String sensorId){
        DateFormat df = new SimpleDateFormat(TIMESTAMP_FORMAT);
        df.setTimeZone(TimeZone.getTimeZone("Europe/Amsterdam"));
        String timeTwelveHoursAgo = df.format(new Date(System.currentTimeMillis() - (12 * 60 * 60 * 1000)));
        JSONArray result = new SensorDataRoute().getSensorData(sensorId, timeTwelveHoursAgo, DateTimeUtil.getTimeStamp(), null, "frame");
        if(!result.isEmpty()) {
            // calculate average interval in last 12 hours
            long totalTime = 0;
            for(int i = 1; i < result.size(); i++){
                BasicDBObject previousDataPoint = (BasicDBObject) result.get(i - 1);
                BasicDBObject dataPoint = (BasicDBObject) result.get(i);
                long dateDiff = DateTimeUtil.getDateDiff(previousDataPoint.get(DBNames.TIMESTAMP).toString(), dataPoint.get(DBNames.TIMESTAMP).toString(), TimeUnit.SECONDS);
                totalTime += dateDiff;
            }
            long averageInterval = totalTime / (result.size() - 1);
            // check if last data point is not to long ago
            BasicDBObject lastDataPoint = (BasicDBObject) result.get(result.size() - 1);
            long timeSinceLastDataPoint = DateTimeUtil.getDateDiff(lastDataPoint.get(DBNames.TIMESTAMP).toString(), DateTimeUtil.getTimeStamp(), TimeUnit.SECONDS);
            String status;
            if (timeSinceLastDataPoint > (30 * averageInterval)){
                // inactive if last data point more than 30 intervals ago
                status = "inactive";
            } else if (timeSinceLastDataPoint > (3 * averageInterval)) {
                // intermittent failures if last data point more than 3 intervals ago
                status = "intermittent failures";
            } else {
                // active
                status = "active";
            }
            DBQuery.updateQuery(DBNames.getSensorData(), new BasicDBObject(DBNames.SENSOR_ID, sensorId), new BasicDBObject("status", status));
        } else {
            // set sensor inactive when no sensor data is found for the last 12 hours
            DBQuery.updateQuery(DBNames.getSensorData(), new BasicDBObject(DBNames.SENSOR_ID, sensorId), new BasicDBObject("status", "inactive"));
        }
    }
}
