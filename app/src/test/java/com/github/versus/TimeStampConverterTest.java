package com.github.versus;

import static org.junit.Assert.assertEquals;

import com.github.versus.offline.TimeStampConverter;
import com.github.versus.posts.Timestamp;

import org.junit.Test;

import java.time.Month;
import java.util.Calendar;

public final class TimeStampConverterTest {
    @Test
    public void convertTimeStampWorks() {
        Timestamp ts = new Timestamp(Calendar.getInstance().get(Calendar.YEAR), Month.JANUARY,
                1, 8, 1, Timestamp.Meridiem.PM);
        assertEquals("2023-01-01 08:01:00 PM", TimeStampConverter.convertTimeStamp(ts));
    }

    @Test
    public void convertTimeStampBackWorks() {
        Timestamp ts = new Timestamp(Calendar.getInstance().get(Calendar.YEAR), Month.JANUARY,
                1, 8, 1, Timestamp.Meridiem.PM);
        String conversion = "2023-01-01 08:01:00 PM";
        assertEquals(TimeStampConverter.convertBackToTimeStamp(conversion), ts);
    }

    @Test
    public void convertAndConvertBackAreInverses() {
        Timestamp ts = new Timestamp(Calendar.getInstance().get(Calendar.YEAR), Month.JANUARY,
                1, 8, 1, Timestamp.Meridiem.PM);
        assertEquals(TimeStampConverter
                .convertBackToTimeStamp(TimeStampConverter.convertTimeStamp(ts)), ts);
    }

    @Test
    public void GameFragmentTimestampWorks() {
        String date = "18 April , 2023";
        Timestamp timestamp = new Timestamp(2023, Month.APRIL,
                18, 4, 0, Timestamp.Meridiem.PM);
        assertEquals(timestamp, TimeStampConverter.GameFragmentTimestamp(date));
    }
}
