package util;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Created by Gebruiker on 27-3-2017.
 */
public class TimeFrameUtil {
    private static final Logger LOGGER = LogManager.getLogger(TimeFrameUtil.class);
    private TimeFrameUtil(){}

    public static String getTimeFrame(long difference){
        // less or equal than 2 hours
        if(difference <= 1000 * 3600 * 2){
            LOGGER.debug("frame");
            return "frame";
        }
        // between 2 and 10 hours
        else if(difference > 1000 * 3600 * 2 && difference <= 1000 * 3600 * 10){
            LOGGER.debug("quarter");
            return "quarter";
        }
        // between 10 hours and 7 days
        else if(difference > 1000 * 3600 * 10 && difference <= 1000 * 3600 * 24 * 7){
            LOGGER.debug("hour");
            return "hour";
        }
        // between a week and 4 weeks
        else if(difference > 1000 * 3600 * 24 * 7 && difference <= 1000 * 3600 * 24 * 7 * 4){
            LOGGER.debug("day");
            return "day";
        }
        // more than 4 weeks
        else {
            LOGGER.debug("week");
            return "week";
        }
    }
}
