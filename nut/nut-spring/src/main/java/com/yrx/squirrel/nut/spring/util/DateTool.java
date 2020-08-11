package com.yrx.squirrel.nut.spring.util;

import java.util.Calendar;
import java.util.Date;

/**
 * Created by r.x on 2020/8/11.
 */
public class DateTool {
    private DateTool() {
    }

    public static Date today() {
        Calendar instance = Calendar.getInstance();
        instance.set(Calendar.HOUR_OF_DAY, 0);
        instance.set(Calendar.MINUTE, 0);
        instance.set(Calendar.SECOND, 0);
        instance.set(Calendar.MILLISECOND, 0);

        return instance.getTime();
    }
}
