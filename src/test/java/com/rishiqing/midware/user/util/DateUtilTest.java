package com.rishiqing.midware.user.util;

import org.junit.Test;

import java.util.Date;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Created by  on 2017/7/10.Wallace
 */
public class DateUtilTest {

    @Test
    public void test_compare(){
        Date now = new Date();
        Date before = new Date(now.getTime() - 3600000);
        Date after = new Date(now.getTime() + 3600000);

        int r1 = DateUtil.compare(before, now);
        int r2 = DateUtil.compare(now, now);
        int r3 = DateUtil.compare(after, now);

        assertTrue("r1 should be -1", r1 == -1);
        assertEquals("r2 should be 0", 0, r2);
        assertEquals("r3 should be 1", 1, r3);
    }

    @Test
    public void test_compare2(){
        Date now = new Date();
        Date before = new Date(now.getTime() - 3600000);
        Date after = new Date(now.getTime() + 3600000);

        int r1 = DateUtil.compare(before, now);
        int r2 = DateUtil.compare(now, now);
        int r3 = DateUtil.compare(after, now);

        assertTrue("r1 should be -1", r1 == -1);
        assertEquals("r2 should be 0", 0, r2);
        assertEquals("r3 should be 1", 1, r3);
    }
}
