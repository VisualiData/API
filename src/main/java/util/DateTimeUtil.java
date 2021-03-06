package util;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;
import java.util.concurrent.TimeUnit;

/*
 * Class for DateTime methods
 */
public class DateTimeUtil {
    private static final Logger LOGGER = LogManager.getLogger(DateTimeUtil.class);
    private static final String TIMESTAMP_FORMAT = "yyyy-MM-dd'T'HH:mm:ss";
    private DateTimeUtil(){}

    public static String getTimeStamp(){
        DateFormat df = new SimpleDateFormat(TIMESTAMP_FORMAT);
        df.setTimeZone(TimeZone.getTimeZone("Europe/Amsterdam"));
        return df.format(new Date(System.currentTimeMillis()));
    }

    public static String parseDateTime(long milliseconds){
        DateFormat df = new SimpleDateFormat(TIMESTAMP_FORMAT);
        df.setTimeZone(TimeZone.getTimeZone("Europe/Amsterdam"));
        return df.format(new Date(milliseconds));
    }

    public static String getDate(String timeFrame, boolean current){
        String format = "yyyy-MM-dd'T'00:00:00.00ZZ";
        int quarterAdjustment = 0;
        if ("frame".equals(timeFrame)) {
            format = "yyyy-MM-dd'T'HH:mm:00.00ZZ";
        }
        else if ("quarter".equals(timeFrame)){
            format = "yyyy-MM-dd'T'HH:00:00.00ZZ";
        }
        if(!current){
            switch (timeFrame){
                case "quarter":
                    quarterAdjustment = 4;
                    break;
                case "hour":
                    quarterAdjustment = 96;
                    break;
                case "day" :
                    quarterAdjustment = 672;
                    break;
                default:
                    quarterAdjustment = 1;
                    break;
            }
        }
        DateFormat df=new SimpleDateFormat(format);
        df.setTimeZone(TimeZone.getTimeZone("UTC"));
        return df.format(new Date(System.currentTimeMillis() - 3600 * 250 * quarterAdjustment));
    }
    public static long getDateDiff(String date1, String date2, TimeUnit timeUnit) {
        DateFormat format = new SimpleDateFormat(TIMESTAMP_FORMAT);
        long diffInMilliseconds;
        try{
            diffInMilliseconds = format.parse(date2).getTime() - format.parse(date1).getTime();
        }catch (ParseException e){
            LOGGER.error(e);
            diffInMilliseconds = 0;
        }
        return timeUnit.convert(diffInMilliseconds, TimeUnit.MILLISECONDS);
    }
}
