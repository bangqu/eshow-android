package com.bangqu.lib.utils;

import android.support.annotation.NonNull;
import android.text.format.DateFormat;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by Administrator on 2016/6/21.
 */
public class DateFormatUtil {

    public static final String DATE_FORMAT = "yyyy-MM-dd";
    public static final String TIMESTAMP_FORMAT = "yyyy-MM-dd kk:mm:ss";

    /*格式化时间*/
    @NonNull
    public static String getDate2Str(Date d, String format) {
        return DateFormat.format(format, d).toString();
    }

    /*获取当前格式化时间*/
    @NonNull
    public static String getCurrentTimeFormat(String format) {
        Calendar calendar = Calendar.getInstance();
        return DateFormat.format(format, calendar.getTime()).toString();
    }

    /*获取当前时间戳*/
    @NonNull
    public static String getCurrentTimeStamp() {
        Calendar calendar = Calendar.getInstance();
        return DateFormat.format(TIMESTAMP_FORMAT, calendar.getTime()).toString();
    }

    /*字符串转日期格式*/
    public static Date getString2Date(String date, String format) {
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        try {
            return sdf.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return new Date();
    }

    public static String getCountDownTimer(long timer) {
        if (timer <= 0) {
            return "已结束";
        }
        StringBuffer sb = new StringBuffer();
        int day = (int) (timer / (24 * 3600));
        timer = timer % (24 * 3600);
        int hour = (int) (timer / 3600);
        timer = timer % 3600;
        int minute = (int) (timer / 60);
        int second = (int) (timer % 60);
        if (day > 0) {
            sb.append(day);
            sb.append("天");
            sb.append(hour);
            sb.append("时");
            sb.append(minute);
            sb.append("分");
            sb.append(second);
            sb.append("秒");
        } else {
            return getTimeStr(hour, minute, second);
        }
        return sb.toString();
    }

    public static String getBlanceDays(String start, String end) {
        long time = getString2Date(end, TIMESTAMP_FORMAT).getTime() - getString2Date(start, TIMESTAMP_FORMAT).getTime();
        String res = "";
        time = time / 1000;
        if (time <= 60) {
            res = "1分钟";
        } else if (time > 60 && time < 3600) {
            res = time / 60 + "分钟";
        } else if (time >= 3600 && time < 3600 * 24) {
            res = time / 3600 + "小时";
        } else {
            res = time / (3600 * 24) + "天";
        }
        return res;
    }

    public static String getTimeStr(int hour, int minute, int second) {
        StringBuffer sb = new StringBuffer();
        if (hour < 10) {
            sb.append("0");
        }
        sb.append(hour);
        sb.append(":");
        if (minute < 10) {
            sb.append("0");
        }
        sb.append(minute);
        sb.append(":");
        if (second < 10) {
            sb.append("0");
        }
        sb.append(second);
        return sb.toString();
    }

}
