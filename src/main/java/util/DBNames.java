package util;

/*
 * Names as used in DB
 */

public class DBNames {
    // sensor info collection
    public static final String SENSOR_DATA = "sensorData";
    // sensor info fields
    public static final String SENSOR_ID = "sensor_id";
    public static final String SENSOR_HOUSE_ID = "position.house";
    public static final String SENSOR_FLOOR_ID = "position.floor";

    // Sensor data fields
    public static final String TIMESTAMP = "timestamp";
    public static final String TIME_FRAME = "timeframe";

    // house info collection
    public static final String HOUSE = "house";
    // house info fields
    public static final String HOUSE_ID = "house_id";

    private DBNames(){}
}
