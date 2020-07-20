package com.ubtech.gallery_lib.util;

import java.util.Formatter;
import java.util.Locale;

public class TimeUtil {

    public static String stringForTime(int timeMs){
        StringBuilder stringBuilder = new StringBuilder();
        Formatter formatter = new Formatter(stringBuilder, Locale.getDefault());
        int totalSeconds = timeMs/1000;
        int seconds = totalSeconds % 60;
        int minutes = (totalSeconds/60)%60;
        int hours = totalSeconds/3600;
        stringBuilder.setLength(0);
        if(hours>0){
            return formatter.format("%d:%02d:%02d",hours,minutes,seconds).toString();
        } else {
            return formatter.format("%02d:%02d",minutes,seconds).toString();
        }
    }
}
