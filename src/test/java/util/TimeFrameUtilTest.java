package util;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class TimeFrameUtilTest {
    private long hourInMilliseconds = 1000 * 3600;
    private long dayInMilliseconds = hourInMilliseconds * 24;

    @Test
    public void getFrameTimeFrame() {
        assertEquals("An hour must return frame", "frame", TimeFrameUtil.getTimeFrame(hourInMilliseconds));
        assertEquals("Two hours must return frame", "frame", TimeFrameUtil.getTimeFrame(hourInMilliseconds * 2));
    }

    @Test
    public void getQuarterTimeFrame(){
        assertEquals("Three hours must return quarter", "quarter", TimeFrameUtil.getTimeFrame(hourInMilliseconds * 3));
        assertEquals("Ten hours must return quarter", "quarter", TimeFrameUtil.getTimeFrame(hourInMilliseconds * 10));
    }

    @Test
    public void getHourTimeFrame(){
        assertEquals("Eleven hours must return hour", "hour", TimeFrameUtil.getTimeFrame(hourInMilliseconds * 11));
        assertEquals("A day must return hour", "hour", TimeFrameUtil.getTimeFrame(dayInMilliseconds));
        assertEquals("A week must return hour", "hour", TimeFrameUtil.getTimeFrame(dayInMilliseconds * 7));
    }

    @Test
    public void getDayTimeFrame(){
        assertEquals("Eight days must return day", "day", TimeFrameUtil.getTimeFrame(dayInMilliseconds * 8));
        assertEquals("4 weeks must return day", "day", TimeFrameUtil.getTimeFrame(dayInMilliseconds * 7 * 4));
    }

    @Test
    public void getWeekTimeFrame(){
        assertEquals("29 days must return week", "week", TimeFrameUtil.getTimeFrame(dayInMilliseconds * 29));
        assertEquals("26 weeks must return week", "week", TimeFrameUtil.getTimeFrame(dayInMilliseconds * 7 * 26));
    }
}
