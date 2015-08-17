package org.opencloudengine.serviceportal.util;

import java.text.SimpleDateFormat;
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
}
