package util;

import org.junit.Test;

import java.util.concurrent.TimeUnit;

import static org.junit.Assert.assertEquals;

public class DateTimeUtilTest {
    private long hourInMilliseconds = 1000 * 3600;

    @Test
    public void getDifference(){
        assertEquals("An hour", hourInMilliseconds, DateTimeUtil.getDateDiff("2017-01-02T10:00:00", "2017-01-02T11:00:00", TimeUnit.MILLISECONDS));
        assertEquals("An hour", hourInMilliseconds * 24, DateTimeUtil.getDateDiff("2017-01-02T10:00:00", "2017-01-03T10:00:00", TimeUnit.MILLISECONDS));
    }
}
