package com.study.common;

import org.apache.logging.log4j.Level;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * The type Date util.
 * @preserve public
 */
public final class DateUtil {

	private static final String DEFAULT_TIME_FORMAT = "yyyy-MM-dd HH:mm:ss";
	private static final int SIXTY = 60;

	private DateUtil() {
	}

	/**
	 * 两个日期比较
	 * @param obj1 the obj 1
	 * @param obj2 the obj 2
	 * @return  boolean
     */
	public static boolean compareDate(Object obj1,Object obj2){
		if(obj1==null || obj2==null){
			return false;
		}else{
			Date date1 = stringToDate(obj1, "yyyy-MM-dd");
			Date date2 = stringToDate(obj2, "yyyy-MM-dd");
			return date1.after(date2);
		}
	}

	/**
	 * 时间转换String
	 * @param date the date
	 * @param format the format
	 * @return  string
     */
	public static String dateToString(Date date, String format){
		if(date==null){
			return "";
		}
		SimpleDateFormat sdf = new SimpleDateFormat(format);

		return sdf.format(date);
	}

	/**
	 * String 转换Date
	 * @param obj the obj
	 * @param format the format
	 * @return  date
     */
	public static Date stringToDate(Object obj, String format){
		Date date = null;
		try {
			if(obj != null && !StringUtil.isEmpty(obj.toString().trim())){
				date = new SimpleDateFormat(format).parse(obj.toString().trim());
			}
		} catch (ParseException e) {
			StudyLogger.recSysLog(Level.ERROR, e.getMessage(), e);
			date = null;
		}
		return date;
	}

	/**
	 * String 转换Date 如果String 空 默认取当前时间
	 * @param obj the obj
	 * @param format the format
	 * @return  date
     */
	public static Date stringToDate2NewDate(Object obj, String format){
		Date date = new Date();
		try {
			if(obj != null && obj.toString().trim().length() > 0){
				date = new SimpleDateFormat(format).parse(obj.toString().trim());
			}
		} catch (ParseException e) {
			StudyLogger.recSysLog(Level.ERROR, e.getMessage(), e);
			date = new Date();
		}
		return date;
	}

	/**
	 *  获取当前时间,请注意format 24小时制 HH:mm:ss
	 * @param format the format
	 * @return  string
     */
	public static String getCurrentTimeMillis(String format){
		  Date nowTime = new Date(System.currentTimeMillis());
		  SimpleDateFormat sdFormatter = new SimpleDateFormat(format);
		  return sdFormatter.format(nowTime);
	}

	/**
	 *  获取当前时间,默认 24小时制 HH:mm:ss
	 * @return  string
     */
	public static String getCurrentTimeMillis(){
		  Date nowTime = new Date(System.currentTimeMillis());
		  SimpleDateFormat sdFormatter = new SimpleDateFormat(DEFAULT_TIME_FORMAT);
		  return sdFormatter.format(nowTime);
	}

	/**
	 * 获取当前时间
	 * @return  date
     */
	public static Date getCurrentTime(){
		  return new Date(System.currentTimeMillis());
	}

	/**
	 * 把时间化为秒   HH:MM:SS
	 * @param obj the obj
	 * @return  int
     */
	public static int dateToSecond(Object obj){
		if(obj==null){
			return 0;
		}
		String date = obj.toString().trim();
		if(StringUtil.isEmpty(date)){
			return 0;
		}
		int hour = Integer.parseInt(date.split(":")[0]);
		int minute = Integer.parseInt(date.split(":")[1]);
		int second = Integer.parseInt(date.split(":")[2]);
		return hour*SIXTY*SIXTY+minute*SIXTY+second;
	}

	/**
	 * Second to date.
	 *
	 * @param seconds the seconds
	 * @return the string
     */
	public static String secondToDate(int seconds){
		if(seconds==0){
			return "00:00:00:00";
		}else{
			int day = seconds/(SIXTY*SIXTY*SIXTY);
			int hour = (seconds%(SIXTY*SIXTY*SIXTY))/(SIXTY*SIXTY);
			int minute = ((seconds%(SIXTY*SIXTY*SIXTY))%(SIXTY*SIXTY))/SIXTY;
			int second = ((seconds%(SIXTY*SIXTY*SIXTY))%(SIXTY*SIXTY))%SIXTY;
			return NumberUtil.pad(StringUtil.convertToString(day), 2)+":"+
					NumberUtil.pad(StringUtil.convertToString(hour), 2)+":"+
					NumberUtil.pad(StringUtil.convertToString(minute), 2)+":"+
					NumberUtil.pad(StringUtil.convertToString(second), 2);
		}
	}
	
	//-----------------------------------------------------------------------------------------------------------------------

	/**
	 * 取得两个日期相差的天数
	 *
	 * @param beginDate the begin date
	 * @param endDate the end date
	 * @return  day margin
     */
	public static int getDayMargin(Date beginDate, Date endDate) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(beginDate);
		calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH),
				calendar.get(Calendar.DAY_OF_MONTH), 1, 0, 0);
		long beginTime = calendar.getTime().getTime();
		calendar.setTime(endDate);
		calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH),
				calendar.get(Calendar.DAY_OF_MONTH), 1, 0, 0);
		long endTime = calendar.getTime().getTime();
		return (int) ((endTime - beginTime) / (1000 * SIXTY * SIXTY * 24));
	}

	/**
	 * 计算第二个时间与第一个时间相差分钟数
	 *
	 * @param beginDate the begin date
	 * @param endDate the end date
	 * @return  minute margin
     */
	public static int getMinuteMargin(Date beginDate, Date endDate) {
		long result = (endDate.getTime() / (SIXTY * 1000))
				- (beginDate.getTime() / (SIXTY * 1000));
		return (int) result;
	}


	/**
	 * 判断两个日期是否为同一天
	 *
	 * @param d1             日期一
	 * @param d2             日期二
	 * @return 同一天true ，不是同一天false
     */
	public static boolean isSameDay(Date d1, Date d2) {
		boolean result = false;
		Calendar c1 = Calendar.getInstance();
		c1.setTime(d1);
		Calendar c2 = Calendar.getInstance();
		c2.setTime(d2);
		if (c1.get(Calendar.YEAR) == c2.get(Calendar.YEAR)
				&& c1.get(Calendar.MONTH) == c2.get(Calendar.MONTH)
				&& c1.get(Calendar.DAY_OF_MONTH) == c2
						.get(Calendar.DAY_OF_MONTH)) {
			result = true;
		}
		return result;
	}

	/**
	 * Gets hour minute.
	 *
	 * @param date the date
	 * @return the hour minute
     */
	public static String getHourMinute(Date date) {
		return new SimpleDateFormat("HH:mm").format(date);
	}

	/**
	 * 取得一个年月日组合的长度为8的字符串
	 *
	 * @param date the date
	 * @return  yMD
     */
	public static String getYMD(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		String id = calendar.get(Calendar.YEAR)
				+ isAddZero(calendar.get(Calendar.MONTH) + 1)
				+ isAddZero(calendar.get(Calendar.DAY_OF_MONTH));
		return id;
	}

	/**
	 * Is add zero.
	 *
	 * @param a the a
	 * @return the string
     */
	public static String isAddZero(int a) {
		String s = "";
		if (a < 10) {
			s = "0" + a;
		} else {
			s += a;
		}
		return s;

	}

	/**
	 * 取得一个时间所在月的第一天
	 *
	 * @param date the date
	 * @return  current mondth one
     */
	public static String getCurrentMondthOne(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		return calendar.get(Calendar.YEAR) + "-"
				+ (calendar.get(Calendar.MONTH) + 1) + "-01";
	}

	/**
	 * 根据一个标准的字符串获得一个时间
	 *
	 * @param str the str
	 * @return  date by str
     */
	public static Date getDateByStr(String str) {
		try {
			return new SimpleDateFormat(DEFAULT_TIME_FORMAT).parse(str);
		} catch (ParseException e) {
			StudyLogger.recSysLog(Level.ERROR, e.getMessage(), e);
		}
		return null;
	}

	/**
	 * 获取当前年
	 * @return  current year
	 * @throws ParseException the parse exception
     */
	public static int getCurrentYear() throws ParseException{
		Date d = new Date(System.currentTimeMillis());
		Calendar c = Calendar.getInstance();
		c.setTime(d);
		int year = c.get(Calendar.YEAR);
//		int month = c.get(Calendar.MONTH) + 1;
//		int day = c.get(Calendar.DAY_OF_MONTH);
		return year;
	}

	/**
	 * 获取当前月
	 * @return  current month
	 * @throws ParseException the parse exception
     */
	public static int getCurrentMonth() throws ParseException{
		Date d = new Date(System.currentTimeMillis());
		Calendar c = Calendar.getInstance();
		c.setTime(d);
//		int year = c.get(Calendar.YEAR);
		int month = c.get(Calendar.MONTH) + 1;
//		int day = c.get(Calendar.DAY_OF_MONTH);
		return month;
	}

	/**
	 * Gets date by str.
	 *
	 * @param str the str
	 * @param format the format
	 * @return the date by str
     */
	public static Date getDateByStr(String str, String format) {
		try {
			return new SimpleDateFormat(format).parse(str);
		} catch (ParseException e) {
			StudyLogger.recSysLog(Level.ERROR, e.getMessage(), e);
		}
		return null;
	}

	/**
	 * Gets timestamp by str.
	 *
	 * @param str the str
	 * @param format the format
	 * @return the timestamp by str
     */
	public static Timestamp getTimestampByStr(String str, String format) {
		try {
			Date date=new SimpleDateFormat(format).parse(str);
			return new Timestamp(date.getTime());
		} catch (ParseException e) {
			StudyLogger.recSysLog(Level.ERROR, e.getMessage(), e);
		}
		return null;
	}

	/**
	 * Gets crruent time.
	 *
	 * @return the crruent time
     */
	public static String getCrruentTime() {
		return new SimpleDateFormat(DEFAULT_TIME_FORMAT).format(new Date());
	}


	/**
	 * Gets crruent time.
	 *
	 * @param format the format
	 * @return the crruent time
     */
	public static String getCrruentTime(String format) {
		return new SimpleDateFormat(format).format(new Date());
	}

	/**
	 * Get week today.
	 *
	 * @return the int
     */
	public static int getWeekToday(){
        Calendar c = Calendar.getInstance();
        c.setTime(new Date(System.currentTimeMillis()));
        int dayOfWeek = c.get(Calendar.DAY_OF_WEEK);

        return dayOfWeek-1;
    }

	/**
	 * Gets crruent timestamp.
	 *
	 * @return the crruent timestamp
     */
	public static Timestamp getCrruentTimestamp() {
		return new Timestamp(System.currentTimeMillis());
	}

	/**
	 * Gets time.
	 *
	 * @param timestamp the timestamp
	 * @return the time
     */
	public static String getTime(Timestamp timestamp) {
		return new SimpleDateFormat(DEFAULT_TIME_FORMAT).format(timestamp);
	}

	/**
	 * Gets time.
	 *
	 * @param timestamp the timestamp
	 * @param format the format
	 * @return the time
     */
	public static String getTime(Timestamp timestamp, String format) {
		return new SimpleDateFormat(format).format(timestamp);
	}

	/**
	 * 给当期指定的日期加上指定的天数
	 * @param now 指定的日期
	 * @param day 添加的天数
	 * @return  date by add day
     */
	public static Date getDateByAddDay(Date now, int day)
	{
		return setDate(now, Calendar.DAY_OF_MONTH,day);
	}

	/**
	 * 给当期指定的日期加上指定的天数
	 * @param now 指定的日期
	 * @param month 添加的月份数
	 * @return  date by add month
     */
	public static Date getDateByAddMonth(Date now, int month)
	{
		return setDate(now, Calendar.MONTH,month);
	}
	
	/**
	 * 给当期指定的日期加上指定的月份
	 * @param now  指定的日期
	 * @param field  日期字段：YEAR，MONTH， DAY_OF_MONTH  WEEK_OF_MONTH， DAY_OF_WEEK， DAY_OF_WEEK_IN_MONTH， WEEK_OF_YEAR
					   时间字段：HOUR_OF_DAY AM_PM + HOUR
	 * @param value 加减的量制
	 * @return
	 */
	private static Date setDate(Date now , int field, int value)
	{
		if(now==null) {
			return null;
		}
		Calendar cad= Calendar.getInstance();
		cad.setTime(now);

		cad.add(field, value);
		return cad.getTime();
	}

	/**
	 * 日期加减
	 * @param now the now
	 * @param value 正数为加   负数为减
	 * @return  string
     */
	public static String addAndMinusDate(String now,int value){
		Calendar calendar=Calendar.getInstance(); 
		Date nowDate = stringToDate(now, DEFAULT_TIME_FORMAT);
		calendar.setTime(nowDate);
		calendar.set(Calendar.DAY_OF_MONTH,calendar.get(Calendar.DAY_OF_MONTH)+value);
		return dateToString(calendar.getTime(), DEFAULT_TIME_FORMAT);
	}

	/**
	 * 当前时间加减分钟数
	 * @param value the value
	 * @return  string
     */
	public static String addAndMinusMinute(int value){
		Calendar calendar=Calendar.getInstance(); 
		Date nowDate = getCurrentTime();
		calendar.setTime(nowDate);
		calendar.set(Calendar.MINUTE,calendar.get(Calendar.MINUTE)+value);
		return dateToString(calendar.getTime(), DEFAULT_TIME_FORMAT);
	}

	/**
	 * Get time format begin.
	 *
	 * @param page the page
	 * @param size the size
	 * @return the string
     */
	public static String getTimeFormatBegin(int page,int size){

        Calendar calendar=Calendar.getInstance();
        Date nowDate = getCurrentTime();
        calendar.setTime(nowDate);
        calendar.add(Calendar.DAY_OF_MONTH, -page*size);

        return dateToString(calendar.getTime(), "yyyy-MM")+"-01";
    }

	/**
	 * Get time format end.
	 *
	 * @param page the page
	 * @param size the size
	 * @return the string
     */
	public static String getTimeFormatEnd(int page,int size){

        Calendar calendar=Calendar.getInstance();
        Date nowDate = getCurrentTime();
        calendar.setTime(nowDate);
        calendar.add(Calendar.DAY_OF_MONTH, -page*size);
      //  calendar.add(calendar.DATE,1);

        return dateToString(calendar.getTime(), "yyyy-MM-dd");
    }

//	public static void main(String[] args) {
    //    System.out.println(getTimeFormatBegin(1,2));
//		Date now = new Date();
//	      Calendar cal = Calendar.getInstance();
//
//	      DateFormat d1 = DateFormat.getDateInstance(); //默认语言（汉语）下的默认风格（MEDIUM风格，比如：2008-6-16 20:54:53）
//	      String str1 = d1.format(now);
//	      DateFormat d2 = DateFormat.getDateTimeInstance();
//	      String str2 = d2.format(now);
//	      DateFormat d3 = DateFormat.getTimeInstance();
//	      String str3 = d3.format(now);
//	      DateFormat d4 = DateFormat.getInstance(); //使用SHORT风格显示日期和时间
//	      String str4 = d4.format(now);
//
//	      DateFormat d5 = DateFormat.getDateTimeInstance(DateFormat.FULL,DateFormat.FULL); //显示日期，周，时间（精确到秒）
//	      String str5 = d5.format(now);
//	      DateFormat d6 = DateFormat.getDateTimeInstance(DateFormat.LONG,DateFormat.LONG); //显示日期。时间（精确到秒）
//	      String str6 = d6.format(now);
//	      DateFormat d7 = DateFormat.getDateTimeInstance(DateFormat.SHORT,DateFormat.SHORT); //显示日期，时间（精确到分）
//	      String str7 = d7.format(now);
//	      DateFormat d8 = DateFormat.getDateTimeInstance(DateFormat.MEDIUM,DateFormat.MEDIUM); //显示日期，时间（精确到分）
//	      String str8 = d8.format(now);//与SHORT风格相比，这种方式最好用
//
//	      System.out.println("用Date方式显示时间: " + now);//此方法显示的结果和Calendar.getInstance().getTime()一样
//
//	      System.out.println("用DateFormat.getDateInstance()格式化时间后为：" + str1);
//	      System.out.println("用DateFormat.getDateTimeInstance()格式化时间后为：" + str2);
//	      System.out.println("用DateFormat.getTimeInstance()格式化时间后为：" + str3);
//	      System.out.println("用DateFormat.getInstance()格式化时间后为：" + str4);
//
//	      System.out.println("用DateFormat.getDateTimeInstance(DateFormat.FULL,DateFormat.FULL)格式化时间后为：" + str5);
//	      System.out.println("用DateFormat.getDateTimeInstance(DateFormat.LONG,DateFormat.LONG)格式化时间后为：" + str6);
//	      System.out.println("用DateFormat.getDateTimeInstance(DateFormat.SHORT,DateFormat.SHORT)格式化时间后为：" + str7);
//	      System.out.println("用DateFormat.getDateTimeInstance(DateFormat.MEDIUM,DateFormat.MEDIUM)格式化时间后为：" + str8);
//	}

}
