package util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;
import java.util.concurrent.TimeUnit;

public class DateTimeUtil {
    private DateTimeUtil(){}

    public static String getTimeStamp(){
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        df.setTimeZone(TimeZone.getTimeZone("Europe/Amsterdam"));
        return df.format(new Date(System.currentTimeMillis()));
    }

    public static String GetDate(String timeFrame, boolean current){
        String format = "yyyy-MM-dd'T'00:00:00.00ZZ";
        int HoursAdjustment = 0;
        if ("frame".equals(timeFrame)) {
            format = "yyyy-MM-dd'T'HH:mm:ss.SSZZ";
        }
        if(!current){
            switch (timeFrame){
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
    public static long getDateDiff(String date1, String date2, TimeUnit timeUnit) {
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSZZ");
        long diffInMillies;
        try{
            diffInMillies = format.parse(date2).getTime() - format.parse(date1).getTime();
        }catch (ParseException e){
            diffInMillies = 0;
        }

        return timeUnit.convert(diffInMillies, TimeUnit.MILLISECONDS);
    }
}
