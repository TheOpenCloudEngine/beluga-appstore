package org.opencloudengine.serviceportal.util;

/**
 * Created by swsong on 2015. 8. 18..
 */
public class ParseUtil {

    public static Long parseLong(Object s) {
        return parseLong(s, null);
    }
    public static Long parseLong(Object s, Long defaultValue) {
        try {
            return new Long(Long.parseLong((String) s));
        } catch (Exception e) {
            return defaultValue;
        }
    }

    public static Float parseFloat(Object s) {
        return parseFloat(s, null);
    }
    public static Float parseFloat(Object s, Float defaultValue) {
        try {
            return new Float(Float.parseFloat((String) s));
        } catch (Exception e) {
            return defaultValue;
        }
    }

    public static Integer parseInt(Object s) {
        return parseInt(s, null);
    }
    public static Integer parseInt(Object s, Integer defaultValue) {
        try {
            return new Integer(Integer.parseInt((String) s));
        } catch (Exception e) {
            return defaultValue;
        }
    }

    public static Character parseChar(String s) {
        if(s != null && s.length() > 0) {
            return s.charAt(0);
        }
        return null;
    }
}
