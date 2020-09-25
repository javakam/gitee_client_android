package com.ando.toolkit;

import android.annotation.SuppressLint;
import android.text.TextUtils;

import androidx.annotation.StringDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * Title:TimeUtils
 * <p>
 * Description: 时间工具类
 * </p>
 *
 * @author javakam
 * @date 2019/12/11 10:09
 */
public class TimeUtils {

    public static final String DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";
    public static final String DATE_FORMAT1 = "yyyy-MM-dd HH:mm";
    public static final String DATE_FORMAT2 = "yyyy-MM-dd";
    public static final String DATE_FORMAT3 = "yyyy.MM.dd";
    public static final String DATE_FORMAT4 = "yyyy年MM月dd日 HH:mm:ss";
    public static final String DATE_FORMAT5 = "yyyy年MM月dd日 HH:mm";
    public static final String DATE_FORMAT6 = "yyyy年M月dd日";
    public static final String DATE_FORMAT7 = "yyyyMMddHHmmss";

    @StringDef({DATE_FORMAT, DATE_FORMAT1, DATE_FORMAT2, DATE_FORMAT3, DATE_FORMAT4, DATE_FORMAT5, DATE_FORMAT6})
    @Retention(RetentionPolicy.SOURCE)
    @interface Format {
    }

    private static SimpleDateFormat sdf(@Format String in) {
        if (isBlank(in)) {
            in = "1970-01-01 00:00:00";
        }
        return new SimpleDateFormat(in, Locale.getDefault());
    }

    public static String dateToString(Date date) {
        return dateToString(date, DATE_FORMAT);
    }

    public static String dateToString(Date date, @Format String in) {
        return sdf(in).format(date);
    }

    private static boolean isBlank(final CharSequence cs) {
        int strLen;
        if (cs == null || (strLen = cs.length()) == 0) {
            return true;
        }
        for (int i = 0; i < strLen; i++) {
            if (!Character.isWhitespace(cs.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    /**
     * 获取今天的时间
     *
     * @return int[] 三个元素: 年,月,日
     */
    public static int[] getCurrentDateTime() {
        final Calendar now = Calendar.getInstance();
        return new int[]{now.get(Calendar.YEAR), (now.get(Calendar.MONTH) + 1), now.get(Calendar.DAY_OF_MONTH)};
    }

    /**
     * 获取现在时间 yyyy-MM-dd HH:mm:ss
     */
    public static Date getCurrentDateTimeDate() {
        final SimpleDateFormat formatter = sdf(DATE_FORMAT);
        return formatter.parse(formatter.format(new Date()), new ParsePosition(8));
    }

    /**
     * 获取现在星期几
     */
    public static int getCurrentWeek() {
        final Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        return calendar.get(Calendar.DAY_OF_WEEK) - 1;
    }


    /**
     * 把一个毫秒数转化成时间字符串。格式为小时/分/秒/毫秒（如：24903600 --> 06小时55分03秒600毫秒）。
     *
     * @param millis   要转化的毫秒数。
     * @param isWhole  是否强制全部显示小时/分/秒/毫秒。
     * @param isFormat 时间数字是否要格式化，如果true：少位数前面补全；如果false：少位数前面不补全。
     * @return 返回时间字符串：小时/分/秒/毫秒的格式（如：24903600 --> 06小时55分03秒600毫秒）。
     */
    public static String millisToString(long millis, boolean isWhole, boolean isFormat) {
        String h = "";
        String m = "";
        String s = "";
        String mi = "";
        if (isWhole) {
            h = isFormat ? "00小时" : "0小时";
            m = isFormat ? "00分" : "0分";
            s = isFormat ? "00秒" : "0秒";
            mi = isFormat ? "00毫秒" : "0毫秒";
        }

        long temp = millis;

        long hper = 60 * 60 * 1000;
        long mper = 60 * 1000;
        long sper = 1000;

        if (temp / hper > 0) {
            if (isFormat) {
                h = temp / hper < 10 ? "0" + temp / hper : temp / hper + "";
            } else {
                h = temp / hper + "";
            }
            h += "小时";
        }
        temp = temp % hper;

        if (temp / mper > 0) {
            if (isFormat) {
                m = temp / mper < 10 ? "0" + temp / mper : temp / mper + "";
            } else {
                m = temp / mper + "";
            }
            m += "分";
        }
        temp = temp % mper;

        if (temp / sper > 0) {
            if (isFormat) {
                s = temp / sper < 10 ? "0" + temp / sper : temp / sper + "";
            } else {
                s = temp / sper + "";
            }
            s += "秒";
        }
        temp = temp % sper;
        mi = temp + "";

        if (isFormat) {
            if (temp < 100 && temp >= 10) {
                mi = "0" + temp;
            }
            if (temp < 10) {
                mi = "00" + temp;
            }
        }

        mi += "毫秒";
        return h + m + s + mi;
    }

    /**
     * 把一个毫秒数转化成时间字符串。格式为小时/分/秒/毫秒（如：24903600 --> 06小时55分03秒）。
     *
     * @param millis   要转化的毫秒数。
     * @param isWhole  是否强制全部显示小时/分/秒/毫秒。
     * @param isFormat 时间数字是否要格式化，如果true：少位数前面补全；如果false：少位数前面不补全。
     * @return 返回时间字符串：小时/分/秒/毫秒的格式（如：24903600 --> 06小时55分03秒）。
     */
    public static String millisToStringMiddle(long millis, boolean isWhole, boolean isFormat) {
        return millisToStringMiddle(millis, isWhole, isFormat, "小时", "分钟", "秒");
    }

    public static String millisToStringMiddle(long millis, boolean isWhole, boolean isFormat, String hUnit, String mUnit, String sUnit) {
        String h = "";
        String m = "";
        String s = "";
        if (isWhole) {
            h = isFormat ? "00" + hUnit : "0" + hUnit;
            m = isFormat ? "00" + mUnit : "0" + mUnit;
            s = isFormat ? "00" + sUnit : "0" + sUnit;
        }

        long temp = millis;

        long hper = 60 * 60 * 1000;
        long mper = 60 * 1000;
        long sper = 1000;

        if (temp / hper > 0) {
            if (isFormat) {
                h = temp / hper < 10 ? "0" + temp / hper : temp / hper + "";
            } else {
                h = temp / hper + "";
            }
            h += hUnit;
        }
        temp = temp % hper;

        if (temp / mper > 0) {
            if (isFormat) {
                m = temp / mper < 10 ? "0" + temp / mper : temp / mper + "";
            } else {
                m = temp / mper + "";
            }
            m += mUnit;
        }
        temp = temp % mper;

        if (temp / sper > 0) {
            if (isFormat) {
                s = temp / sper < 10 ? "0" + temp / sper : temp / sper + "";
            } else {
                s = temp / sper + "";
            }
            s += sUnit;
        }
        return h + m + s;
    }

    /**
     * 把一个毫秒数转化成时间字符串。格式为小时/分/秒/毫秒（如：24903600 --> 06小时55分钟）。
     *
     * @param millis   要转化的毫秒数。
     * @param isWhole  是否强制全部显示小时/分。
     * @param isFormat 时间数字是否要格式化，如果true：少位数前面补全；如果false：少位数前面不补全。
     * @return 返回时间字符串：小时/分/秒/毫秒的格式（如：24903600 --> 06小时55分钟）。
     */
    public static String millisToStringShort(long millis, boolean isWhole, boolean isFormat) {
        String h = "";
        String m = "";
        if (isWhole) {
            h = isFormat ? "00小时" : "0小时";
            m = isFormat ? "00分钟" : "0分钟";
        }

        long temp = millis;

        long hper = 60 * 60 * 1000;
        long mper = 60 * 1000;

        if (temp / hper > 0) {
            if (isFormat) {
                h = temp / hper < 10 ? "0" + temp / hper : temp / hper + "";
            } else {
                h = temp / hper + "";
            }
            h += "小时";
        }
        temp = temp % hper;

        if (temp / mper > 0) {
            if (isFormat) {
                m = temp / mper < 10 ? "0" + temp / mper : temp / mper + "";
            } else {
                m = temp / mper + "";
            }
            m += "分钟";
        }

        return h + m;
    }

    /**
     * 把日期毫秒转化为字符串。默认格式：yyyy-MM-dd HH:mm:ss
     *
     * @param millis 要转化的日期毫秒数。
     * @return 返回日期字符串（如："2013-02-19 11:48:31"）。
     */
    public static String millisToStringDate(long millis) {
        return millisToStringDate(millis, DATE_FORMAT);
    }

    /**
     * 把日期毫秒转化为字符串。
     *
     * @param millis  要转化的日期毫秒数。
     * @param pattern 要转化为的字符串格式（如：yyyy-MM-dd HH:mm:ss）。
     * @return 返回日期字符串。
     */
    @SuppressLint("SimpleDateFormat")
    public static String millisToStringDate(long millis, String pattern) {
        return new SimpleDateFormat(pattern).format(new Date(millis));

    }

    /**
     * 把日期毫秒转化为字符串（文件名）。
     *
     * @param millis  要转化的日期毫秒数。
     * @param pattern 要转化为的字符串格式（如：yyyy-MM-dd HH:mm:ss）。
     * @return 返回日期字符串（yyyy_MM_dd_HH_mm_ss）。
     */
    public static String millisToStringFilename(long millis, String pattern) {
        String dateStr = millisToStringDate(millis, pattern);
        return dateStr.replaceAll("[- :]", "_");
    }

    /**
     * 把日期毫秒转化为字符串（文件名）。
     *
     * @param millis 要转化的日期毫秒数。
     * @return 返回日期字符串（yyyy_MM_dd_HH_mm_ss）。
     */
    public static String millisToStringFilename(long millis) {
        String dateStr = millisToStringDate(millis, "yyyy-MM-dd HH:mm:ss");
        return dateStr.replaceAll("[- :]", "_");
    }


    private static long oneHourMillis = 60 * 60 * 1000; // 一小时的毫秒数
    private static long oneDayMillis = 24 * oneHourMillis; // 一天的毫秒数
    private static long oneYearMillis = 365 * oneDayMillis; // 一年的毫秒数


    public static String millisToLifeStringPHPZQRB(String millis) {
        long millisLong = StringUtils.isBlank(millis) ? 0 : Long.parseLong(millis);
        if (millisLong <= 0) {
            return "";
        }
        return millisToLifeStringPHPZQRB(millisLong);
    }

    public static String millisToLifeStringPHPZQRB(long millisLong) {
        if (millisLong <= 0) {
            return "";
        }
        millisLong *= 1000;//PHP时间要 * 1000

        long now = System.currentTimeMillis();
        long todayStart = string2Millis(millisToStringDate(now, "yyyy-MM-dd"), "yyyy-MM-dd");

        if (now - millisLong <= oneHourMillis && now - millisLong > 0) { // 一小时内
            String m = millisToStringShort(now - millisLong, false, false);
            return "".equals(m) ? "1分钟内" : m + "前";
        }

        if (millisLong >= todayStart && millisLong <= oneDayMillis + todayStart) { // 大于今天开始开始值，小于今天开始值加一天（即今天结束值）
            return "今天 " + millisToStringDate(millisLong, "HH:mm");
        }

        if (millisLong > todayStart - oneDayMillis) { // 大于（今天开始值减一天，即昨天开始值）
            return "昨天";
        }

        if (millisLong > todayStart - oneDayMillis * 2) {
            return "前天";
        }

        double diff = (double) (todayStart - millisLong) / oneDayMillis;
        int diffDay = (int) Math.ceil(diff);
        //L.w("TimeUtils diffDay= " + diff + "   " + diffDay);
        if (diffDay <= 10) {// 大于 10 天显示具体时间
            //return diffDay + "天前 "+ millisToStringDate(millisLong, "yyyy年MM月dd日 HH:mm");//用于调试
            return diffDay + "天前 ";
        }
        //?不准?
//        if (millisLong >= todayStart - oneDayMillis * 4 && millisLong <= todayStart - oneDayMillis * 10) { // 大于 10 天显示具体时间
//            return diffDay + "天前";
//        }

        long thisYearStart = string2Millis(millisToStringDate(now, "yyyy"), "yyyy");
        if (millisLong > thisYearStart) { // 大于今天小于今年
            return millisToStringDate(millisLong, "yyyy年MM月dd日");
        }

        return millisToStringDate(millisLong, "yyyy年MM月dd日");
    }

    /**
     * millis "1578455259"
     */
    public static String millisToLifeStringPHP(String millis) {
        long millisLong = StringUtils.isBlank(millis) ? 0 : Long.parseLong(millis);
        return millisToLifeStringPHP(millisLong);
    }

    /**
     * Fixed : PHP 返回的时间戳和Java的不一致导致时间为 1970 的问题
     * <pre>
     *      1563423720L    System.currentTimeMillis() -> 1574392167822
     * </pre>
     * 时间格式：
     * 1小时内用，多少分钟前；
     * 超过1小时，显示时间而无日期；
     * 如果是昨天，则显示昨天
     * 超过昨天再显示日期；
     * 超过1年再显示年。
     */
    public static String millisToLifeStringPHP(long millis) {
        if (millis <= 0) {
            return "";
        }
        return millisToLifeString(millis * 1000);
    }

    /**
     * 时间格式：
     * 1小时内用，多少分钟前；
     * 超过1小时，显示时间而无日期；
     * 如果是昨天，则显示昨天
     * 超过昨天再显示日期；
     * 超过1年再显示年。
     */
    public static String millisToLifeString(long millis) {
        long now = System.currentTimeMillis();
        long todayStart = string2Millis(millisToStringDate(now, "yyyy-MM-dd"), "yyyy-MM-dd");

        if (now - millis <= oneHourMillis && now - millis > 0) { // 一小时内
            String m = millisToStringShort(now - millis, false, false);
            return "".equals(m) ? "1分钟内" : m + "前";
        }

        if (millis >= todayStart && millis <= oneDayMillis + todayStart) { // 大于今天开始开始值，小于今天开始值加一天（即今天结束值）
            return "今天 " + millisToStringDate(millis, "HH:mm");
        }

        if (millis > todayStart - oneDayMillis) { // 大于（今天开始值减一天，即昨天开始值）
            return "昨天 " + millisToStringDate(millis, "HH:mm");
        }

        long thisYearStart = string2Millis(millisToStringDate(now, "yyyy"), "yyyy");
        if (millis > thisYearStart) { // 大于今天小于今年
            return millisToStringDate(millis, "MM月dd日 HH:mm");
        }

        return millisToStringDate(millis, "yyyy年MM月dd日 HH:mm");
    }

    /**
     * 时间格式：
     * 今天，显示时间而无日期；
     * 如果是昨天，则显示昨天
     * 超过昨天再显示日期；
     * 超过1年再显示年。
     */
    public static String millisToLifeString2(long millis) {
        long now = System.currentTimeMillis();
        long todayStart = string2Millis(millisToStringDate(now, "yyyy-MM-dd"), "yyyy-MM-dd");

        if (millis > todayStart + oneDayMillis && millis < todayStart + 2 * oneDayMillis) { // 明天
            return "明天" + millisToStringDate(millis, "HH:mm");
        }
        if (millis > todayStart + 2 * oneDayMillis && millis < todayStart + 3 * oneDayMillis) { // 后天
            return "后天" + millisToStringDate(millis, "HH:mm");
        }

        if (millis >= todayStart && millis <= oneDayMillis + todayStart) { // 大于今天开始开始值，小于今天开始值加一天（即今天结束值）
            return "今天 " + millisToStringDate(millis, "HH:mm");
        }

        if (millis > todayStart - oneDayMillis && millis < todayStart) { // 大于（今天开始值减一天，即昨天开始值）
            return "昨天 " + millisToStringDate(millis, "HH:mm");
        }

        long thisYearStart = string2Millis(millisToStringDate(now, "yyyy"), "yyyy");
        if (millis > thisYearStart) { // 大于今天小于今年
            return millisToStringDate(millis, "MM月dd日 HH:mm");
        }

        return millisToStringDate(millis, "yyyy年MM月dd日 HH:mm");
    }

    /**
     * 时间格式：
     * 今天，显示时间而无日期；
     * 如果是昨天，则显示昨天
     * 超过昨天再显示日期；
     * 超过1年再显示年。
     */
    public static String millisToLifeString3(long millis) {
        long now = System.currentTimeMillis();
        long todayStart = string2Millis(millisToStringDate(now, "yyyy-MM-dd"), "yyyy-MM-dd");

        if (millis > todayStart + oneDayMillis && millis < todayStart + 2 * oneDayMillis) { // 明天
            return "明天";
        }
        if (millis > todayStart + 2 * oneDayMillis && millis < todayStart + 3 * oneDayMillis) { // 后天
            return "后天";
        }

        if (millis >= todayStart && millis <= oneDayMillis + todayStart) { // 大于今天开始开始值，小于今天开始值加一天（即今天结束值）
            return millisToStringDate(millis, "HH:mm");
        }

        if (millis > todayStart - oneDayMillis && millis < todayStart) { // 大于（今天开始值减一天，即昨天开始值）
            return "昨天 ";
        }

        return millisToStringDate(millis, "MM月dd日");
    }

    /**
     * 时间格式：
     * 今天，显示时间而无日期；
     * 如果是昨天，则显示昨天
     * 超过昨天再显示日期；
     * 超过1年再显示年。
     */
    public static String millisToLifeString4(long millis) {
        long now = System.currentTimeMillis();
        long todayStart = string2Millis(millisToStringDate(now, "yyyy-MM-dd"), "yyyy-MM-dd");

        if (millis > todayStart + oneDayMillis && millis < todayStart + 2 * oneDayMillis) { // 明天
            return "明天" + millisToStringDate(millis, "HH:mm");
        }
        if (millis > todayStart + 2 * oneDayMillis && millis < todayStart + 3 * oneDayMillis) { // 后天
            return "后天" + millisToStringDate(millis, "HH:mm");
        }

        if (millis >= todayStart && millis <= oneDayMillis + todayStart) { // 大于今天开始开始值，小于今天开始值加一天（即今天结束值）
            return "今天 " + millisToStringDate(millis, "HH:mm");
        }

        if (millis > todayStart - oneDayMillis && millis < todayStart) { // 大于（今天开始值减一天，即昨天开始值）
            return "昨天 " + millisToStringDate(millis, "HH:mm");
        }

        long thisYearStart = string2Millis(millisToStringDate(now, "yyyy"), "yyyy");
        if (millis > thisYearStart) { // 大于今天小于今年
            return millisToStringDate(millis, "MM月dd日 HH:mm");
        }

        return millisToStringDate(millis, "yyyy-MM-dd HH:mm");
    }

    /**
     * 字符串解析成毫秒数
     */
    public static long string2Millis(String str, String pattern) {
        SimpleDateFormat format = new SimpleDateFormat(pattern, Locale.getDefault());
        long millis = 0;
        try {
            millis = format.parse(str).getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return millis;
    }

    /**
     * 获得今天开始的毫秒值
     */
    public static long getTodayStartMillis() {
        return getOneDayStartMillis(System.currentTimeMillis());
    }

    private static long getOneDayStartMillis(long millis) {
        String dateStr = millisToStringDate(millis, "yyyy-MM-dd");
        return string2Millis(dateStr, "yyyy-MM-dd");
    }

    /**
     * 字符串转日期
     * <p>
     * yyyy-MM-dd HH:mm:ss  -> Date 对象
     */
    public static Date strToDate(String str) {
        if (str == null) {
            str = "1970-01-01 00:00:00";
        }
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat(
                    "yyyy-MM-dd HH:mm:ss", Locale.getDefault());
            return dateFormat.parse(str);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }


    /**
     * 获取现在时间
     *
     * @return返回短时间格式 yyyy-MM-dd
     */
    public static Date getNowDateyyyyMMdd() {
        Date currentTime = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat(DATE_FORMAT2);
        String dateString = formatter.format(currentTime);
        ParsePosition pos = new ParsePosition(8);
        Date currentTime_2 = formatter.parse(dateString, pos);
        return currentTime_2;
    }

    /**
     * 获取现在时间
     *
     * @return返回字符串格式 yyyy-MM-dd HH:mm:ss
     */
    public static String getDateSecondStr() {
        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
    }

    /**
     * 获取当前时间
     *
     * @return 返回格式 yyyy-MM-dd HH:mm
     */
    public static String getDateMinuteStr() {
        return new SimpleDateFormat("yyyy-MM-dd HH:mm").format(new Date());
    }

    /**
     * 获取当前时间 -- 今天的年月日
     *
     * @return 返回格式 yyyy-MM-dd
     */
    public static String getStringDateyyyy_MM_dd() {
        Date currentTime = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat(DATE_FORMAT2);
        String date = formatter.format(currentTime);
        return date;
    }

    /**
     * 获取当前时间
     *
     * @return 返回格式 yyyy.MM.dd
     */
    public static String getStringDateyyyyMMdd() {
        SimpleDateFormat formatter = sdf(DATE_FORMAT3);
        return formatter.format(new Date());
    }

    /**
     * 获取前月的第一天
     *
     * @return yyyy.MM.dd
     */
    public static String getFirstDayOfThisMonth() {
        Calendar cale = Calendar.getInstance();
        cale.add(Calendar.MONTH, 0);
        cale.set(Calendar.DAY_OF_MONTH, 1);
        return sdf(DATE_FORMAT3).format(cale.getTime());
    }

    /**
     * 获取前月的最后一天
     *
     * @return yyyy.MM.dd
     */
    public static String getLastDayOfThisMonth() {
        Calendar cale = Calendar.getInstance();
        cale.add(Calendar.MONTH, 1);
        cale.set(Calendar.DAY_OF_MONTH, 0);
        return sdf(DATE_FORMAT3).format(cale.getTime());
    }

    /**
     * 获取今年第一天日期
     *
     * @return String 日期格式：今年第一天 2018-01-01
     */
    public static String getCurrYearFirst() {
        final Calendar c = Calendar.getInstance();
        c.add(Calendar.YEAR, 0);
        c.set(Calendar.DAY_OF_YEAR, 1);//设置为1号,当前日期既为本年第一天
        return dateToString(c.getTime());
    }

    /**
     * 获取某年第一天日期
     *
     * @param year 年份  2018
     * @return Date
     */
    public static Date getCurrYearFirst(int year) {
        final Calendar calendar = Calendar.getInstance();
        calendar.clear();
        calendar.set(Calendar.YEAR, year);
        return calendar.getTime();
    }

    /**
     * 获取某年第一天日期
     *
     * @param year 年份  2018
     * @return String
     */
    public static String getCurrYearFirstStr(int year) {
        Calendar calendar = Calendar.getInstance();
        calendar.clear();
        calendar.set(Calendar.YEAR, year);
        return dateToString(calendar.getTime());
    }

    /**
     * 获取某年最后一天日期
     *
     * @param year 年份  2018
     * @return Date
     */
    public static Date getCurrYearLast(int year) {
        Calendar calendar = Calendar.getInstance();
        calendar.clear();
        calendar.set(Calendar.YEAR, year);
        calendar.roll(Calendar.DAY_OF_YEAR, -1);
        return calendar.getTime();
    }

    /**
     * 获取某年最后一天日期
     *
     * @param year 年份  2018
     * @return String
     */
    public static String getCurrYearLastStr(int year) {
        Calendar calendar = Calendar.getInstance();
        calendar.clear();
        calendar.set(Calendar.YEAR, year);
        calendar.roll(Calendar.DAY_OF_YEAR, -1);
        return dateToString(calendar.getTime());
    }

    /**
     * 两时间比较大小
     *
     * @param setDate 日期参数格式 yyyy-MM-dd
     * @param nowDate 日期参数格式 yyyy-MM-dd
     * @return true setDate <= nowDate
     */
    public static boolean compared(String setDate, String nowDate) {
        SimpleDateFormat dateFormat = sdf(DATE_FORMAT2);
        try {
            Date date1 = dateFormat.parse(setDate);
            Date date2 = dateFormat.parse(nowDate);
            if (date1 == null || date2 == null) {
                return false;
            }
            if (date1.getTime() <= date2.getTime()) {
                return true;
            } else {
                return false;
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 两时间比较大小
     *
     * @param setDate 日期参数格式 yyyy-MM-dd HH:mm
     * @param nowDate 日期参数格式 yyyy-MM-dd HH:mm
     * @return true setDate <= nowDate and default
     */
    public static boolean compared2(String setDate, String nowDate) {
        SimpleDateFormat dateFormat = sdf(DATE_FORMAT1);
        try {
            Date date1 = dateFormat.parse(setDate);
            Date date2 = dateFormat.parse(nowDate);
            if (date1 == null || date2 == null) {
                return false;
            }
            if (date1.getTime() <= date2.getTime()) {
                return true;
            } else {
                return false;
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 两时间比较大小
     *
     * @param setDate 日期参数格式 Date
     * @param nowDate 日期参数格式 Date
     * @return true setDate <= nowDate and default
     */
    public static boolean compared2(Date setDate, Date nowDate) {
        if (setDate.getTime() <= nowDate.getTime()) {
            return true;
        }
        return false;
    }

    /**
     * 将日期格式转为毫秒数
     *
     * @param in 格式为 2014-09-30 09:50
     * @return 返回格式为 1345185923140
     */
    public static long dateToLong(@Format String in) {
        try {
            final Date date = sdf(in).parse(in);
            if (date == null) {
                return 0;
            }

            final Calendar cal = Calendar.getInstance();
            cal.setTime(date);
            return cal.getTimeInMillis();
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return 0;
    }

    public static String longToDate(long millis) {
        return longToDate(millis, DATE_FORMAT);
    }

    /**
     * 将毫秒数转为日期
     *
     * @param millis 格式为1345185923140L
     * @return 返回格式为 年-月-日 时：分：秒
     */
    public static String longToDate(long millis, @Format String template) {
        final Calendar gc = Calendar.getInstance();
        gc.setTime(new Date(millis));
        return sdf(template).format(gc.getTime());
    }

    /**
     * 将2015-10-18-16-47-30格式时间转换为 2015年10月18日 16:47
     *
     * @param dateTime
     * @return
     */
    public static String transTime1(String dateTime) {
        if (!TextUtils.isEmpty(dateTime)) {
            final String[] str = dateTime.split("-");
            final StringBuilder sb = new StringBuilder();
            sb.append(str[0])
                    .append("年")
                    .append(str[1])
                    .append("月")
                    .append(str[2])
                    .append("日  ")
                    .append(str[3])
                    .append(":")
                    .append(str[4]);

            return sb.toString();
        } else {
            return "";
        }
    }

    /**
     * 将2015-10-18-16-47-30格式时间转换为 2015-10-18 16:47
     *
     * @param dateTime
     * @return
     */
    public static String transTime2(String dateTime) {
        if (!TextUtils.isEmpty(dateTime)) {
            final String[] str = dateTime.split("-");
            final StringBuilder sb = new StringBuilder();
            sb.append(str[0])
                    .append("-")
                    .append(str[1])
                    .append("-")
                    .append(str[2])
                    .append("   ")
                    .append(str[3])
                    .append(":")
                    .append(str[4]);

            return sb.toString();
        } else {
            return "";
        }
    }

    /**
     * 将2015-10-18-16-47-30格式时间转换为 2015-10-18
     *
     * @param dateTime
     * @return
     */
    public static String transTime3(String dateTime) {
        if (!TextUtils.isEmpty(dateTime)) {
            final String[] str = dateTime.split("-");
            final StringBuilder sb = new StringBuilder();
            sb.append(str[0])
                    .append("-")
                    .append(str[1])
                    .append("-")
                    .append(str[2]);

            return sb.toString();
        } else {
            return "";
        }
    }

    /**
     * 将 yyyy-MM-dd 格式时间转换为 yyyy.MM.dd
     *
     * @param dateTime
     * @return
     */
    public static String transTime4(String dateTime) {
        if (!TextUtils.isEmpty(dateTime)) {
            return dateTime.replace("-", ".");
        } else {
            return "";
        }
    }

    /**
     * 将 yyyy-MM-dd HH:mm:ss 格式时间转换为 yyyy.MM.dd
     *
     * @param dateTime
     * @return
     */
    public static String transTime5(String dateTime) {
        if (!TextUtils.isEmpty(dateTime)) {
            String newTime = dateTime.substring(0, 10);
            return newTime.replace("-", ".");
        } else {
            return "";
        }
    }

    /**
     * 服务器返回时间格式：2015/10/9 0:00:00
     *
     * @param time
     * @return 2015-10-09
     */
    public static String convertTime(String time) {
        if (!TextUtils.isEmpty(time)) {
            String[] str = time.split(" ");
            if (str.length > 1) {
                str[0] = str[0].replaceAll("/", "-");
                String[] ss = str[0].split("-");
                String res = null;
                if (ss.length == 3) {
                    res = ss[0];
                    if (ss[1].length() == 1) {
                        res += "-0" + ss[1];
                    } else {
                        res += "-" + ss[1];
                    }
                    if (ss[2].length() == 1) {
                        res += "-0" + ss[2];
                    } else {
                        res += "-" + ss[2];
                    }
                }
                return res;
            }
        }
        return null;
    }

}