package com.xq.hdb.utils;

import com.alibaba.druid.support.json.JSONUtils;
import org.apache.commons.lang3.time.DateFormatUtils;

import java.lang.management.ManagementFactory;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;

/**
 * 时间工具类
 *
 * @author xiqi
 */
public class DateUtils extends org.apache.commons.lang3.time.DateUtils
{
    public static String YYYY = "yyyy";

    public static String YYYY_MM = "yyyy-MM";

    public static String YYYY_MM_DD = "yyyy-MM-dd";

    public static String YYYYMMDDHHMMSS = "yyyyMMddHHmmss";

    public static String YYYY_MM_DD_HH_MM_SS = "yyyy-MM-dd HH:mm:ss";

    private static String[] parsePatterns = {
            "yyyy-MM-dd", "yyyy-MM-dd HH:mm:ss", "yyyy-MM-dd HH:mm", "yyyy-MM",
            "yyyy/MM/dd", "yyyy/MM/dd HH:mm:ss", "yyyy/MM/dd HH:mm", "yyyy/MM",
            "yyyy.MM.dd", "yyyy.MM.dd HH:mm:ss", "yyyy.MM.dd HH:mm", "yyyy.MM"};

    /**
     * 获取当前Date型日期
     *
     * @return Date() 当前日期
     */
    public static Date getNowDate()
    {
        return new Date();
    }

    /**
     * 获取当前日期, 默认格式为yyyy-MM-dd
     *
     * @return String
     */
    public static String getDate()
    {
        return dateTimeNow(YYYY_MM_DD);
    }

    public static final String getTime()
    {
        return dateTimeNow(YYYY_MM_DD_HH_MM_SS);
    }

    public static final String dateTimeNow()
    {
        return dateTimeNow(YYYYMMDDHHMMSS);
    }

    public static final String dateTimeNow(final String format)
    {
        return parseDateToStr(format, new Date());
    }

    public static final String dateTime(final Date date)
    {
        return parseDateToStr(YYYY_MM_DD, date);
    }

    public static final String parseDateToStr(final String format, final Date date)
    {
        return new SimpleDateFormat(format).format(date);
    }

    public static final Date dateTime(final String format, final String ts)
    {
        try
        {
            return new SimpleDateFormat(format).parse(ts);
        }
        catch (ParseException e)
        {
            throw new RuntimeException(e);
        }
    }

    /**
     * 日期路径 即年/月/日 如2018/08/08
     */
    public static final String datePath()
    {
        Date now = new Date();
        return DateFormatUtils.format(now, "yyyy/MM/dd");
    }

    /**
     * 日期路径 即年/月/日 如20180808
     */
    public static final String dateTime()
    {
        Date now = new Date();
        return DateFormatUtils.format(now, "yyyyMMdd");
    }


    public static final Integer currentMonth(){
        Calendar calendar = Calendar.getInstance();
        //记得要+1
        int month = calendar.get(Calendar.MONTH) + 1;
        return month;
    }



    /**
     * 获取上个月月份
     * @return
     */
    public static final String getLastMonth() {
        SimpleDateFormat format = new SimpleDateFormat("yyyyMM");
        Date date = new Date();
        Calendar calendar = Calendar.getInstance();
        // 设置为当前时间
        calendar.setTime(date);
        calendar.add(Calendar.MONTH,-1);
        date = calendar.getTime();
        return DateFormatUtils.format(date, "yyyyMM");
    }



    /**
     * 获取本月月份
     * @return
     */
    public static final String currentYearMonth() {
        SimpleDateFormat format = new SimpleDateFormat("yyyyMM");
        Date date = new Date();
        Calendar calendar = Calendar.getInstance();
        // 设置为当前时间
        calendar.setTime(date);
        calendar.add(Calendar.MONTH,-0);
        date = calendar.getTime();
        return DateFormatUtils.format(date, "yyyyMM");
    }





    /**
     * 日期型字符串转化为日期 格式
     */
    public static Date parseDate(Object str)
    {
        if (str == null)
        {
            return null;
        }
        try
        {
            return parseDate(str.toString(), parsePatterns);
        }
        catch (ParseException e)
        {
            return null;
        }
    }

    /**
     * 获取服务器启动时间
     */
    public static Date getServerStartDate()
    {
        long time = ManagementFactory.getRuntimeMXBean().getStartTime();
        return new Date(time);
    }

    /**
     * 计算相差天数
     */
    public static int differentDaysByMillisecond(Date date1, Date date2)
    {
        return Math.abs((int) ((date2.getTime() - date1.getTime()) / (1000 * 3600 * 24)));
    }

    /**
     * 计算两个时间差
     */
    public static String getDatePoor(Date endDate, Date nowDate)
    {
        long nd = 1000 * 24 * 60 * 60;
        long nh = 1000 * 60 * 60;
        long nm = 1000 * 60;
        // long ns = 1000;
        // 获得两个时间的毫秒时间差异
        long diff = endDate.getTime() - nowDate.getTime();
        // 计算差多少天
        long day = diff / nd;
        // 计算差多少小时
        long hour = diff % nd / nh;
        // 计算差多少分钟
        long min = diff % nd % nh / nm;
        // 计算差多少秒//输出结果
        // long sec = diff % nd % nh % nm / ns;
        return day + "天" + hour + "小时" + min + "分钟";
    }


    /**
     * 带T时间转换
     * @param timeZone
     * @return
     */
    public static Date timeZoneToDate(String timeZone){
        Date date = null;
        try {
            DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
            //df.setTimeZone(TimeZone.getTimeZone("GMT+8"));
            date = df.parse(timeZone);
            date.setHours(date.getHours() - 8);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }



    /**
     * 带T时间转换
     * @param timeZone
     * @return
     */
    public static String timeZoneToString(String timeZone){
        if(timeZone.equals("null")){
            return null;
        }
        String dateString = null;
        try {
            DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
            Date date = df.parse(timeZone);
            date.setHours(date.getHours() - 8);
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            dateString = formatter.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return dateString;
    }



    public static Date msToDate(Long milliSecond){
        Date date = new Date();
        date.setTime(milliSecond);
        return date;
    }


    /**
     * 获取当天日期毫秒
     * @param date
     * @return
     */
    public static Long theDayStart(Date date){
        if(date == null){
            return null;
        }

        Long ms = 0L;
        try{
            //转成相应格式的字符串
            String dateStr = new SimpleDateFormat("yyyy-MM-dd 00:00:00").format(date);
            //将字符时间转成特定Date
            ms = new SimpleDateFormat("yyyy-MM-dd 00:00:00").parse(dateStr).getTime();
        }catch (Exception e){
            e.printStackTrace();
        }
        return ms;
    }



    /**
     * 获取当天日期
     * @param date
     * @return
     */
    public static Date getDayStart(Date date){
        if(date == null){
            return null;
        }
        Date day = null;

        try{
            SimpleDateFormat sdf = new SimpleDateFormat();// 格式化时间
            sdf.applyPattern("yyyy-MM-dd 00:00:00");// a为am/pm的标记
            String dateStr=sdf.format(date);
            SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
            day = sdf1.parse(dateStr);

        }catch (Exception e){
            e.printStackTrace();
        }
        return day;
    }




    /**
     * 获取当天日期
     * @param date
     * @return
     */
    public static Date getDayEnd(Date date){
        Date day = null;
        SimpleDateFormat sdf = new SimpleDateFormat();// 格式化时间
        sdf.applyPattern("yyyy-MM-dd 00:00:00");// a为am/pm的标记
        String dateStr=sdf.format(date);
        try{
            SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
            day = sdf1.parse(getSpecifiedDayAfter(dateStr));
        }catch (Exception e){
            e.printStackTrace();
        }
        return day;
    }




    /**

     * 获得指定日期的后一天

     *

     * @param specifiedDay

     * @return

     */

    public static String getSpecifiedDayAfter(String specifiedDay) {

        Calendar c = Calendar.getInstance();

        Date date = null;

        try {

            date = new SimpleDateFormat("yy-MM-dd").parse(specifiedDay);

        } catch (ParseException e) {

            e.printStackTrace();

        }

        c.setTime(date);

        int day = c.get(Calendar.DATE);

        c.set(Calendar.DATE, day + 1);

        String dayAfter = new SimpleDateFormat("yyyy-MM-dd").format(c.getTime());

        return dayAfter;

    }




    /**
     * 获取后一天日期毫秒
     * @param date
     * @return
     */
    public static Long theDayEnd(Date date){
        if(date == null){
            return null;
        }
        Long ms = theDayStart(date)+24*60*60*1000 - 1;
        return ms;
    }

    /**
     * 将字符转为日期
     * @param time
     * @return
     */
    public static String strToDateStr(String time) {
        String str="";
        try {
            SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
            SimpleDateFormat sd=new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSX");
            Date date=sd.parse(time);
            str=sdf.format(date);
        } catch(Exception e) {
            return str;
        }
        return str;
    }




}
