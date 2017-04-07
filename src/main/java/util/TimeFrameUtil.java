package util;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Class for time frame methods
 */
public class TimeFrameUtil {
    private static final Logger LOGGER = LogManager.getLogger(TimeFrameUtil.class);
    private TimeFrameUtil(){}

    public static String getTimeFrame(long timeDifference){
        long hourInMilliseconds = 3600000;
        // less or equal than 2 hours
        if(timeDifference <= hourInMilliseconds * 2){
            LOGGER.debug("frame");
            return "frame";
        }
        // between 2 and 10 hours
        else if(timeDifference > hourInMilliseconds * 2 && timeDifference <= hourInMilliseconds * 10){
            LOGGER.debug("quarter");
            return "quarter";
        }
        // between 10 hours and 7 days
        else if(timeDifference > hourInMilliseconds * 10 && timeDifference <= hourInMilliseconds * 24 * 7){
            LOGGER.debug("hour");
            return "hour";
        }
        // between a week and 4 weeks
        else if(timeDifference > hourInMilliseconds * 24 * 7 && timeDifference <= hourInMilliseconds * 24 * 28){
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
