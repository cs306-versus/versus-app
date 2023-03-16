package com.github.versus.posts;

import java.net.MulticastSocket;
import java.time.Month;
import java.time.Year;
import java.time.YearMonth;
import java.util.Calendar;

public class Timestamp {

    private static final int CURR_YEAR = Calendar.getInstance().get(Calendar.YEAR);
    private static final int MAX_YEAR = CURR_YEAR + 1;
    private final int year;
    private final Month month;
    private final int day;

    private final int hour;
    private final int minutes;
    private final int seconds;
    private final Meridiem m;

    public Timestamp(int year, Month month, int day, int hour, int minutes, Meridiem m) {
        // TODO : enable assertions for these checks to happen
        // acceptable time and date checks
        assert( CURR_YEAR <= year && year <= MAX_YEAR );
        assert( 1 <= day && day <= 31 );
        assert( 1 <=  hour && hour <= 12 );
        assert( 1 <=  minutes && minutes <= 60 );

        this.year = year;
        this.month = month;
        this.day = day;
        this.hour = hour;
        this.minutes = minutes;
        this.seconds = 0;
        this.m = m;
    }
    private Timestamp(){
        this.year = 0;
        this.month = null;
        this.day = 0;
        this.hour = 0;
        this.minutes = 0;
        this.seconds = 0;
        this.m = null;
    }


    /**
     *
     * @return the day of the timestamp
     */
    public int getDay() {
        return day;
    }

    /**
     *
     * @return the month of the timestamp
     */
    public Month getMonth() {
        return month;
    }

    /**
     *
     * @return the year of the timestamp
     */
    public int getYear() {
        return year;
    }
    /**
     *
     * @return the hour of the timestamp
     */
    public int getHour() {
        return hour;
    }
    /**
     *
     * @return the minutes of the timestamp
     */
    public int getMinutes() {
        return minutes;
    }
    /**
     *
     * @return the seconds of the timestamp
     */
    public int getSeconds() {
        return seconds;
    }
    /**
     *
     * @return the meridiem of the timestamp
     */
    public Meridiem getMeridiem() {
        return m;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof Timestamp)) {
            return false;
        }
        Timestamp other = (Timestamp) obj;
        return this.getYear() == other.getYear()
                && this.getMonth().equals(other.getMonth())
                && this.getDay() == other.getDay()
                && this.getHour() == other.getHour()
                && this.getMinutes() == other.getMinutes()
                && this.getSeconds() == other.getSeconds()
                && this.getMeridiem().equals(other.getMeridiem());
    }




    /**
     * AM-PM enum
     */
    public enum Meridiem{
        AM, PM;
    }}
