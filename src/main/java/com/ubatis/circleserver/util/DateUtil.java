package com.ubatis.circleserver.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.regex.Pattern;

/**
 * 日期工具，包含用于获取特定的日期的方法
 *
 * @author Administrator
 * @2011-12-12
 */
public class DateUtil {

    public final static int DAYSECOND = 86400;

	/**
	 * 普通日期格式化 "yyyy-MM-dd HH:mm:ss"
	 */
	public static SimpleDateFormat simpleDateFormat = new SimpleDateFormat(
			"yyyy-MM-dd HH:mm:ss", Locale.getDefault());

	public static SimpleDateFormat dateFormatWithoutSecond = new SimpleDateFormat(
			"yyyy-MM-dd HH:mm", Locale.getDefault());

	/**
	 * 普通日期格式化 "yyyy-MM-dd"
	 */
	public static SimpleDateFormat dateFormatWithourHour = new SimpleDateFormat(
			"yyyy-MM-dd", Locale.getDefault());

	public static SimpleDateFormat dateFormat4CosPath = new SimpleDateFormat("yyyy/MM", Locale.getDefault());

	public static Date getNDayBeforeAt0Oclock(int n) {
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DAY_OF_YEAR, -n);

		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);

		return cal.getTime();
	}

	public static Date getNDayLaterAt0Oclock(int n) {
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DAY_OF_YEAR, n);

		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);

		return cal.getTime();
	}

	public static Date getNDayLaterAt0LastSec(int n) {
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DAY_OF_YEAR, n);

		cal.set(Calendar.HOUR_OF_DAY, 23);
		cal.set(Calendar.MINUTE, 59);
		cal.set(Calendar.SECOND, 59);

		return cal.getTime();
	}

	public static Date getNDayBeforeAt0LastSec(int n) {
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DAY_OF_YEAR, -n);

		cal.set(Calendar.HOUR_OF_DAY, 23);
		cal.set(Calendar.MINUTE, 59);
		cal.set(Calendar.SECOND, 59);

		return cal.getTime();
	}

	public static Date getLastSunDayAt0() {
		Calendar cal = Calendar.getInstance();
		int dayOfWeek = cal.get(Calendar.DAY_OF_WEEK);

		cal.add(Calendar.DAY_OF_YEAR, -(dayOfWeek - 1));

		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		return cal.getTime();
	}

	public static int getHourOfDay() {
		return Calendar.getInstance().get(Calendar.HOUR_OF_DAY);
	}

	public static Date getLastLastSunDayAt0() {
		Calendar cal = Calendar.getInstance();
		int dayOfWeek = cal.get(Calendar.DAY_OF_WEEK);

		cal.add(Calendar.DAY_OF_YEAR, -(dayOfWeek - 1 + 7));

		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		return cal.getTime();
	}

	public static Date getYesterdayAt0() {
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DAY_OF_YEAR, -1);
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);

		return cal.getTime();
	}

	// 获取当月第一天
	public static Date getFirstDayOfMonthAt0() {
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.DAY_OF_MONTH, 1);
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		return cal.getTime();
	}

	// 2018-09-24
	public static Date getTodayAt0() {
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		return cal.getTime();
	}

	// 2018-09-24
	public static String getYearMonthDayStr() {
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		int year = cal.get(Calendar.YEAR);
		int month = cal.get(Calendar.MONTH) + 1;
		int day = cal.get(Calendar.DAY_OF_MONTH);
		StringBuilder sb = new StringBuilder();
		sb.append(year);
		sb.append("-");
		sb.append((month > 9 ? "" : "0") + month);
		sb.append("-");
		sb.append((day > 9 ? "" : "0") + day);
		return sb.toString();
	}
	// 20180924
	public static String getYearMonthDayStrNoSplit() {
		return getYearMonthDayStr().replaceAll("-","");
	}
	// 180927
	public static String getNotFullYearMonthDayStrNoSplit() {
		return getYearMonthDayStr().replaceAll("-","").substring(2);
	}

	public static String getTodayTimetag(){
		return dateFormatWithourHour.format(getTodayAt0());
	}

	public static String getThisMonthTimetag(){
		return dateFormat4CosPath.format(getTodayAt0());
	}

	//当前时间
	public static int getCurrentTimestamp(){
		return (int) (System.currentTimeMillis() / 1000);
	}

	/**
	 * 用默认格式 格式化日期
	 *
	 * @param date
	 * @return
	 */
	public static String toDateString(Date date) {
		return simpleDateFormat.format(date);
	}

	public static String toDateStringWithoutSecond(Date date) {
		return dateFormatWithoutSecond.format(date);
	}

	/**
	 * 用默认格式 格式化日期
	 *
	 * @param date
	 * @return
	 */
	public static String toDateStringWithoutHour(Date date) {
		return dateFormatWithourHour.format(date);
	}

	/**
	 * 判断数字是否 min<= n <= max
	 *
	 * @param n
	 * @param min
	 * @param max
	 * @return
	 */
	public static boolean between(long n, long min, long max) {
		return n >= min && n <= max;
	}

    /**
     * 把时间戳转化为时间格式
     */
    public static String timeStamp2Date(Object timestampString, SimpleDateFormat formats){
        if(timestampString != null){
            Long time = Long.parseLong(timestampString + "000");
            if(time == 0){
                return "";
            }
            java.sql.Timestamp ts = new java.sql.Timestamp(time);
            String date = formats.format(ts);
            return date;
        } else {
            return null;
        }
    }

	/**
	 * 把时间戳转化为时间格式
	 */
    public static String timeStamp2Date(Object timestampString){
        return timeStamp2Date(timestampString, dateFormatWithourHour);
    }

	/**
	 * 把时间戳转化为:日期 + 时间
	 */
    public static String timeStamp2DateTime(Object timestampString){
        return timeStamp2Date(timestampString, simpleDateFormat);
    }

	/**
	 * 返回当天的日期0点的时间戳
	 * @return
	 */
	public static int getTimesmorning() {
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.MILLISECOND, 0);
		return (int) (cal.getTimeInMillis() / 1000);
	}

	public static String getDateTime(){
		return simpleDateFormat.format(new Date());
	}

	/**
	 * 返回当天的日期24点的时间戳
	 * @return
	 */
	public static int getTimesevening() {
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.HOUR_OF_DAY, 23);
		cal.set(Calendar.MINUTE, 59);
		cal.set(Calendar.SECOND, 59);
		cal.set(Calendar.MILLISECOND, 59);
		return (int) (cal.getTimeInMillis() / 1000);
	}


	/**
	 * 默认的日期时间格式
	 */
	static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	static SimpleDateFormat style1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	static SimpleDateFormat style2 = new SimpleDateFormat("yyyy-MM-dd");
	static SimpleDateFormat style3 = new SimpleDateFormat("yyyy/MM/dd");
	static SimpleDateFormat style4 = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");

    /**
     * 格式化日期
     * @param object 日期对象
     * @param format 日期格式
     * @return
     */
	public static String parser2Format(Object object, String format){
		if(object == null){
			return null;
		}
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        Date parser = parser(object);
        if(parser == null){
            return "";
        }
        return sdf.format(parser);
	}

    /**
     * 解析后返回：日期
     * @param object
     * @return
     */
	public static String parser2Date(Object object){
	    if(object == null){
	        return "";
        }
        return style2.format(parser(object));
	}

    /**
     * 解析后返回：日期
     * @param object
     * @return
     */
	public static String parser2DateTime(Object object){
	    if(object == null){
	        return "";
        }
        return style1.format(parser(object));
	}

	/**
	 * 遍历常用时间格式 将字符串转为date对象
	 *
	 * @param obj
	 * @return
	 */

	public static Date parser(Object obj) {
	    if(obj == null){
	        return null;
        }
        String date = obj.toString();
        if (date.contains(".")) {
			date = date.replace(".0", "");
		}
		try {
			// yyyy-mm-dd
			Pattern check2 = Pattern
					.compile("^([0-9]{4})-(0[0-9]{1}|1[0-2])-(0[0-9]{1}|[12][0-9]{1}|3[01])");
			// yyyy-mm-dd hh:mm:ss
			Pattern check1 = Pattern
					.compile("^([0-9]{4})-(0[0-9]{1}|1[0-2])-(0[0-9]{1}|[12][0-9]{1}|3[01]) (0[0-9]{1}|[0-1]{1}[0-9]{1}|2[0-4]{1}):([0-5]{1}[0-9]{1}):([0-5]{1}[0-9]{1})");
			// yyyy/mm/dd
			Pattern check3 = Pattern
					.compile("^([0-9]{4})/(0[0-9]{1}|1[0-2]|[1-9]{1})/(0[0-9]{1}|[12][0-9]{1}|3[01]|[1-9]{1})");
			// yyyy/mm/dd hh:mm:ss
			Pattern check4 = Pattern
					.compile("^([0-9]{4})/(0[0-9]{1}|1[0-2]|[1-9]{1})/(0[0-9]{1}|[12][0-9]{1}|3[01]|[1-9]{1}) (0[0-9]{1}|[0-1]{1}[0-9]{1}|2[0-4]{1}):([0-5]{1}[0-9]{1}):([0-5]{1}[0-9]{1})");
			// yyyy_mm_dd
			Pattern check5 = Pattern
					.compile("^([0-9]{4})_(0[0-9]{1}|1[0-2]|[1-9]{1})_(0[0-9]{1}|[12][0-9]{1}|3[01]|[1-9]{1})");
			// yyyy_mm
			Pattern check6 = Pattern
					.compile("^([0-9]{4})_(0[0-9]{1}|1[0-2]|[1-9]{1})");
			if (check1.matcher(date).matches()) {
				return style1.parse(date);
			} else if (check2.matcher(date).matches()) {
				return style2.parse(date);
			} else if (check3.matcher(date).matches()) {
				return style3.parse(date);
			} else if (check4.matcher(date).matches()) {
				return style4.parse(date);
			} else if (check5.matcher(date).matches()) {
//				String[] dateSplic = date.split("_");
				String resultString = date.replaceAll("_", "/");
				return style3.parse(resultString);
			} else if (check6.matcher(date).matches()) {
				String resultString = date.replaceAll("_", "/").concat("/00");
				return style3.parse(resultString);
			} else {
				return null;
			}
		} catch (Exception e) {
			throw new RuntimeException("parse date error");
		}

	}

}

