package org.deslre.commons.utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * ClassName: DateUtil
 * Description: 时间工具类
 * Author: Deslrey
 * Date: 2025-05-09 16:58
 * Version: 1.0
 */
public class DateUtil {

    /**
     * 获取当前年份
     *
     * @return 当前年份（格式：yyyy）
     */
    public static String getCurrentYear() {
        Calendar calendar = Calendar.getInstance();
        return String.valueOf(calendar.get(Calendar.YEAR));
    }

    /**
     * 获取当前月份 (01-12)
     *
     * @return 当前月份（格式：MM）
     */
    public static String getCurrentMonth() {
        Calendar calendar = Calendar.getInstance();
        int month = calendar.get(Calendar.MONTH) + 1; // Calendar.MONTH 返回的是 0 到 11 的值
        return String.format("%02d", month); // 格式化成两位数字，不足两位前面加0
    }

    /**
     * 获取当前日期 (01-31)
     *
     * @return 当前日期（格式：dd）
     */
    public static String getCurrentDay() {
        Calendar calendar = Calendar.getInstance();
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        return String.format("%02d", day); // 格式化成两位数字，不足两位前面加0
    }

    /**
     * 获取当前日期的字符串格式 (yyyy-MM-dd)
     *
     * @return 当前日期的字符串（格式：yyyy-MM-dd）
     */
    public static String getCurrentDateString() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return sdf.format(new Date());
    }

    /**
     * 获取当前时间的字符串格式 (yyyy-MM-dd HH:mm:ss)
     *
     * @return 当前时间的字符串（格式：yyyy-MM-dd HH:mm:ss）
     */
    public static String getCurrentDateTimeString() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return sdf.format(new Date());
    }

    public static void main(String[] args) {
        System.out.println("当前年份: " + getCurrentYear());
        System.out.println("当前月份: " + getCurrentMonth());
        System.out.println("当前日期: " + getCurrentDay());
        System.out.println("当前日期字符串: " + getCurrentDateString());
        System.out.println("当前时间字符串: " + getCurrentDateTimeString());
    }
}
