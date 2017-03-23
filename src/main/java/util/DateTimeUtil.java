package util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

public class DateTimeUtil {
    private DateTimeUtil(){}

    public static String getTimeStamp(){
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        df.setTimeZone(TimeZone.getTimeZone("Europe/Amsterdam"));
        return df.format(new Date(System.currentTimeMillis()));
    }
}
