package com.github.versus.posts;

import java.net.MulticastSocket;
import java.time.Month;
import java.time.Year;
import java.time.YearMonth;
import java.util.Calendar;

public class Timestamp {

    private static final int CURR_YEAR = Calendar.getInstance().get(Calendar.YEAR);
    private static final int MAX_YEAR = CURR_YEAR;
    private final int year;
    private final Month month;
    private final int day;

    private final int hour;
    private final int minutes;
    private final int seconds;
    private Meridiem meridiem;

    public Timestamp(int year, Month month, int day, int hour, int minutes, Meridiem meridiem) {
        // TODO : enable assertions for these checks to happen
        //TODO: Tell derouich about the check of minutes and hours
        // acceptable time and date checks

        assert( 1 <= day && day <= 31 );
        assert( 0 <=  hour && hour <= 11 );
        assert( 0 <=  minutes && minutes <= 59 );

        this.year = year;
        this.month = month;
        this.day = day;
        this.hour = hour;
        this.minutes = minutes;
        this.seconds = 0;
        this.meridiem = meridiem;
    }

    private Timestamp(){
        this(CURR_YEAR, Month.FEBRUARY, 1, 0,0,Meridiem.AM );
    }


    /**
     * @return the day of the timestamp
     */
    public int getDay() {
        return day;
    }

    /**
     * @return the month of the timestamp
     */
    public Month getMonth() {
        return month;
    }

    /**
     * @return the year of the timestamp
     */
    public int getYear() {
        return year;
    }

    /**
     * @return the hour of the timestamp
     */
    public int getHour() {
        return hour;
    }

    /**
     * @return the minutes of the timestamp
     */
    public int getMinutes() {
        return minutes;
    }

    /**
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
        return meridiem;
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

    public int isBefore(Timestamp that) {
        if(year != that.year){
            return year - that.year;
        }else{
            if(month.ordinal() != that.month.ordinal()){
                return month.ordinal() -that.month.ordinal() ;
            }else {
                //same month
                if (day != that.day) {
                    return day - that.day;
                } else {
                    //same day
                    int hour1 = hour + ((meridiem == Meridiem.PM) ? 12 : 0);
                    int hour2 = that.hour + ((meridiem == Meridiem.PM) ? 12 : 0);
                    if (hour1 != hour2) {
                        return hour1 - hour2;
                    } else {
                        //same hour
                        if (minutes != that.minutes) {
                            return minutes - that.minutes;
                        } else {
                            return seconds - that.seconds;
                        }
                    }


                }
            }
        }
    }




    /**
     * AM-PM enum
     */
    public enum Meridiem{
        AM, PM;
    }

}
