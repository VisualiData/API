package util;

/**
 * Class for time frame methods
 */
public class TimeFrameUtil {
    private TimeFrameUtil(){}

    public static String getTimeFrame(long timeDifference){
        long hourInMilliseconds = 3600000;
        // less or equal than 2 hours
        if(timeDifference <= hourInMilliseconds * 2){
            return "frame";
        }
        // between 2 and 10 hours
        else if(timeDifference > hourInMilliseconds * 2 && timeDifference <= hourInMilliseconds * 10){
            return "quarter";
        }
        // between 10 hours and 7 days
        else if(timeDifference > hourInMilliseconds * 10 && timeDifference <= hourInMilliseconds * 24 * 7){
            return "hour";
        }
        // between a week and 4 weeks
        else if(timeDifference > hourInMilliseconds * 24 * 7 && timeDifference <= hourInMilliseconds * 24 * 28){
            return "day";
        }
        // more than 4 weeks
        else {
            return "week";
        }
    }
}
