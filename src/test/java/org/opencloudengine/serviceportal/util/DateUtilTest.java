package org.opencloudengine.serviceportal.util;

import org.junit.Test;

/**
 * Created by swsong on 2015. 8. 18..
 */
public class DateUtilTest {

    @Test
    public void testElapsedTime() {
        for(long l = 0 ; l < 48 * 60 * 60 * 1000; l+=1000) {
            String elapsed = DateUtil.getElapsedTime(l);
            System.out.println(l + " : " + elapsed);
        }
    }
}
