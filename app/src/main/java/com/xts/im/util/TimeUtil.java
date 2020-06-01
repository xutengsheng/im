package com.xts.im.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class TimeUtil {
    /**
     * 把一个calender对象转换成8位日期,20200302
     * @param calendar
     * @return
     */
    public static String parseTime(Calendar calendar){
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
        return format.format(calendar.getTime());
    }
}
