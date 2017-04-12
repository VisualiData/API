package util;

import org.junit.Test;

import java.util.concurrent.TimeUnit;

import static org.junit.Assert.assertEquals;

public class DateTimeUtilTest {
    private long minuteInMilliseconds = 1000 * 60;
    private long hourInMilliseconds = minuteInMilliseconds * 60;
    private long dayInMilliseconds = hourInMilliseconds * 24;

    @Test
    public void getMinuteDifference(){
        assertEquals("15 minutes", minuteInMilliseconds * 15 , DateTimeUtil.getDateDiff("2017-01-02T10:00:00", "2017-01-02T10:15:00", TimeUnit.MILLISECONDS));
        assertEquals("32 minutes", minuteInMilliseconds * 32, DateTimeUtil.getDateDiff("2017-01-02T10:00:00", "2017-01-02T10:32:00", TimeUnit.MILLISECONDS));
    }

    @Test
    public void getHourDifference(){
        assertEquals("An hour", hourInMilliseconds, DateTimeUtil.getDateDiff("2017-01-02T10:00:00", "2017-01-02T11:00:00", TimeUnit.MILLISECONDS));
        assertEquals("6 hours", hourInMilliseconds * 6, DateTimeUtil.getDateDiff("2017-01-02T10:00:00", "2017-01-02T16:00:00", TimeUnit.MILLISECONDS));
    }

    @Test
    public void getDayDifference(){
        assertEquals("A day", dayInMilliseconds, DateTimeUtil.getDateDiff("2017-01-02T10:00:00", "2017-01-03T10:00:00", TimeUnit.MILLISECONDS));
        assertEquals("7 days", dayInMilliseconds * 7, DateTimeUtil.getDateDiff("2017-01-02T10:00:00", "2017-01-09T10:00:00", TimeUnit.MILLISECONDS));
    }
}
