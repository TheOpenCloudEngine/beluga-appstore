package org.opencloudengine.serviceportal.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by swsong on 2015. 8. 17..
 */
public class DateUtil {

    public static String getNow() {
        return getDateFormat().format(new Date());
    }

    public static SimpleDateFormat getDateFormat() {
        return new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
    }

    public static String convertDateString(String dateString) {
        if(dateString == null) {
            return "";
        }
        return dateString.substring(0, 10).replaceAll("-", ".");
    }

    public static String getElapsedTime(String dateTimeString) {
        if(dateTimeString == null) {
            return "-";
        }
        try {
            Date date = getDateFormat().parse(dateTimeString);
            long diff = System.currentTimeMillis() - date.getTime();
            return getElapsedTime(diff);
        } catch (ParseException ignore) {
        }
        return "";
    }

    public static String getElapsedTime(long elapsed) {
        int seconds = (int) (elapsed / 1000) % 60;
        int minutes = (int) ((elapsed / (1000 * 60)) % 60);
        int hours = (int) ((elapsed / (1000 * 60 * 60)) % 24);
        int days = (int) (elapsed / (1000 * 60 * 60 * 24));

        StringBuilder sb = new StringBuilder();
        if(days != 0) {
            return sb.append(days).append("d").append(" ").append(hours).append("h").toString();
        } else if(hours != 0){
            return sb.append(hours).append("h").append(" ").append(minutes).append("m").toString();
        } else if(minutes != 0){
            return sb.append(minutes).append("m").append(seconds).append("s").toString();
        } else if(seconds != 0){
            return sb.append(seconds).append("s").toString();
        } else {
            return "-";
        }
    }
}
