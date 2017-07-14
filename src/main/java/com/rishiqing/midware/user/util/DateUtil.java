package com.rishiqing.midware.user.util;

import java.util.Date;

/**
 * Created by  on 2017/7/10.Wallace
 */
public class DateUtil {
    public String name;
    /**
     * 如果first > second则，返回1，如果first == second，则返回0，如果first < second，则返回-1
     * @param first
     * @param second
     * @return
     */
    public static int compare(Date first, Date second){
        long firstVal = first.getTime();
        long secondVal = second.getTime();

        if(firstVal > secondVal){
            return 1;
        }else if(firstVal == secondVal){
            return 0;
        }else {
            return -1;
        }
    }
}
