package com.github.versus.offline;


import com.github.versus.posts.Timestamp;

import java.time.Month;

public class TimeStampConverter {

    public static String convertTimeStamp(Timestamp ts){
        return  String.format("%04d-%02d-%02d %02d:%02d:%02d %s", ts.getYear(),
                ts.getMonth().getValue(), ts.getDay(), ts.getHour(), ts.getMinutes(),
                ts.getSeconds(), ts.getMeridiem().name());
    }
    public static Timestamp convertBackToTimeStamp(String conversion){
        String[] components = conversion.split("\\s+");
        int year = Integer.parseInt(components[0].substring(0, 4));
        Month month = Month.of(Integer.parseInt(components[0].substring(5, 7).toUpperCase()));
        int day = Integer.parseInt(components[0].substring(8, 10));
        int hour = Integer.parseInt(components[1].substring(0, 2));
        int minute = Integer.parseInt(components[1].substring(3, 5));
        //TODO tell the team that timestamp always have seconds set to zero
        int seconds = Integer.parseInt(components[1].substring(6, 8));
        Timestamp.Meridiem meridiem = Timestamp.Meridiem.valueOf(components[2].toUpperCase());
    return new Timestamp(year,month,day,hour,minute,meridiem);
    }
}
