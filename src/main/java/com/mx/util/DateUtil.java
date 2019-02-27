package com.mx.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateUtil {

	public final static String DEFAULT_DATE_FORMAT = "yyyy-MM-dd";
	public final static String DEFAULT_DATETIME_FORMAT = "yyyy-MM-dd HH:mm:ss";
	public final static String DEFAULT_DATEMINUTE_FORMAT = "yyyy-MM-dd HH:mm";



	/**
	 * 清除日期时间
	 * @param date
	 */
	public static Date clearTime(Date date){
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.set(Calendar.HOUR_OF_DAY,0);
		cal.set(Calendar.MINUTE,0);
		cal.set(Calendar.SECOND,0);
		cal.set(Calendar.MILLISECOND,0);
		return cal.getTime();
	}
	
		/**
	 * 添加年
	 * @param year
	 * @return
	 */
	public static Date addYear(Date date,int year){
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.YEAR,year);
		return cal.getTime();
	}
	
	/**
	 * 添加月
	 * @param month
	 * @return
	 */
	public static Date addMonth(Date date,int month){
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.MONTH,month);
		return cal.getTime();
	}
	/**
	 * 添加周
	 */
	public static Date addWeek(Date date,int week){
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.WEEK_OF_MONTH, week);
		return cal.getTime();
	}
	/**
	 * 添加天数
	 * @param day
	 * @return
	 */
	public static Date addDay(Date date,int day){
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.DATE,day);
		return cal.getTime();
	}

	/**
	 * 添加小时
	 * @param date
	 * @param hour
	 * @return
	 */
	public static Date addHour(Date date,int hour){
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.HOUR,hour);
		return cal.getTime();
	}

	/**
	 * 添加分
	 * @param date
	 * @param minute
	 * @return
	 */
	public static Date addMinute(Date date,int minute){
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.MINUTE,minute);
		return cal.getTime();
	}
	
	/**
	 * 添加秒
	 */
	public static Date addSecond(Date date,int second){
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.SECOND,second);
		return cal.getTime();
	}
	
	/**
	 * 日期是否包括时间
	 * @param date
	 * @return
	 */
	public static boolean hasTime(Date date){
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		if(cal.get(Calendar.HOUR)>0)return true;
		if(cal.get(Calendar.MINUTE)>0)return true;
		if(cal.get(Calendar.SECOND)>0)return true;
		if(cal.get(Calendar.MILLISECOND)>0)return true;
		return false;
	}
	
	private static Calendar getCalendar(Date date){
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		return cal;
	}
	
	/**
	 * 得到日期天数
	 * @param date
	 * @return
	 */
	public static int getYear(Date date){
		return getCalendar(date).get(Calendar.YEAR);
	}
	
	/**
	 * 得到日期天数
	 * @param date
	 * @return
	 */
	public static int getMonth(Date date){
		return getCalendar(date).get(Calendar.MONTH)+1;
	}
	
	/**
	 * 得到日期天数
	 * @param date
	 * @return
	 */
	public static int getDay(Date date){
		return getCalendar(date).get(Calendar.DATE);
	}
	
	/**
	 * 得到小时
	 * @param date
	 * @return
	 */
	public static int getHour(Date date){
		return getCalendar(date).get(Calendar.HOUR);
	}
	
	/**
	 * 得到分
	 * @param date
	 * @return
	 */
	public static int getMinute(Date date){
		return getCalendar(date).get(Calendar.MINUTE);
	}
	
	/**
	 * 得到秒
	 * @param date
	 * @return
	 */
	public static int getSecond(Date date){
		return getCalendar(date).get(Calendar.SECOND);
	}
	
	/**
	 * 得到毫秒
	 * @param date
	 * @return
	 */
	public static int getMilliSecond(Date date){
		return getCalendar(date).get(Calendar.MILLISECOND);
	}
	
	/**
	 * 得到星期
	 * @return
	 */
	public static int getWeek(Date date){
		return getCalendar(date).get(Calendar.WEDNESDAY);
	}
	
	/**
	 * 得到日期
	 * @return
	 */
	public static Date getDate(int year,int month,int day){
		Calendar cal = Calendar.getInstance();
		cal.set(year, month-1, day, 0, 0, 0);
		return cal.getTime();
	}
	
	/**
	 * 得到日期
	 * @return
	 */
	public static Date getDate(int year,int month,int day,int hour){
		Calendar cal = Calendar.getInstance();
		cal.set(year, month-1, day, hour, 0, 0);
		return cal.getTime();
	}
	
	/**
	 * 得到日期
	 * @return
	 */
	public static Date getDate(int year,int month,int day,int hour,int minute){
		Calendar cal = Calendar.getInstance();
		cal.set(year, month-1, day, hour, minute, 0);
		return cal.getTime();
	}
	
	/**
	 * 得到日期
	 * @return
	 */
	public static Date getDate(int year,int month,int day,int hour,int minute,int second){
		Calendar cal = Calendar.getInstance();
		cal.set(year, month-1, day, hour, minute, second);
		return cal.getTime();
	}
	
	/**
	 * 设置时间
	 * @param date
	 * @param hour
	 * @param minute
	 * @param second
	 * @return
	 */
	public static Date setTime(Date date,int hour,int minute,int second){
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.set(Calendar.HOUR_OF_DAY,hour);
		cal.set(Calendar.MINUTE,minute);
		cal.set(Calendar.SECOND,second);
		cal.set(Calendar.MILLISECOND,0);
		return cal.getTime();
	}
	
	/**
	 * 得到总共的秒数
	 * @param d
	 * @return
	 */
	public static int getTimeTotalSecond(Date d){
		Calendar cal = getCalendar(d);
		return cal.get(Calendar.HOUR)*3600+cal.get(Calendar.MINUTE)*60+cal.get(Calendar.SECOND);
	}

	/**
	 * 获取传入时间月份的第一天日期
	 * @return
	 * @param date
	 */
	public static Date getFirstDateOfMonth(Date date){
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.add(Calendar.MONTH, 0);
		c.set(Calendar.DAY_OF_MONTH,1);
		return c.getTime();
	}

	/**
	 * 获取传入时间月份的最后一天日期
	 * @return
	 * @param date
	 */
	public static Date getLastDateOfMonth(Date date){
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.set(Calendar.DAY_OF_MONTH, c.getActualMaximum(Calendar.DAY_OF_MONTH));
		return c.getTime();
	}

	/**
	 * 得到当前系统时间
	 */
	public static Date getCurrentDate(){
		return Calendar.getInstance().getTime();
	}

	public static long getCurrentTime() {
		return new Date().getTime();
	}

	/**
	 * 
	 * @param date
	 * @return
	 */
	public static Date parser(String date,String format){
		try {
			return new SimpleDateFormat(format).parse(date);
		} catch (ParseException e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * 格式化日期
	 * @param date
	 * @param format
	 * @return
	 */
	public static String format(Date date,String format){
		if (date == null)
			return "";
		return new SimpleDateFormat(format).format(date);
	}

	/**
	 * 比较两个日期的大小
	 */
	public static int compareDate(Date compareDate,Date comparedDate){
		long compareTime = getTimeTotalSecond(compareDate);
		long comparedTime = getTimeTotalSecond(comparedDate);
		return compareTime<comparedTime?1:compareTime==comparedTime?0:-1;
	}

    /**
     * 得到星期中的第几天
     * @return
     */
    public static int getDayOfWeek(Date date){
        return getCalendar(date).get(Calendar.DAY_OF_WEEK);
    }

    /**
     * 得到月中的第几天
     * @return
     */
    public static int getDayOfMonth(Date date){
        return getCalendar(date).get(Calendar.DAY_OF_MONTH);
    }
    
    /**
     * 得到两个日期相差的天数
     * @param startDate
     * @param endDate
     * @return
     */
    public static int getDaySpan(Date startDate,Date endDate){
    	Calendar cal = Calendar.getInstance();
    	cal.setTime(startDate);
    	int y1 = cal.get(Calendar.YEAR);
    	int m1 = cal.get(Calendar.MONTH);
    	int d1 = cal.get(Calendar.DATE);
    	cal.set(y1, m1, d1, 0, 0);
    	long t1 = cal.getTime().getTime();
    	cal.setTime(endDate);
    	int y2 = cal.get(Calendar.YEAR);
    	int m2 = cal.get(Calendar.MONTH);
    	int d2 = cal.get(Calendar.DATE);
    	cal.set(y2, m2, d2, 0, 0);
    	long t2 = cal.getTime().getTime();
    	long DAY = 24L * 60L * 60L * 1000L;  
    	int n = Integer.parseInt(String.valueOf((t2 - t1)/DAY));
    	return n;
    }
    
    
    /**
     * 得到两个日期相差的分钟
     * @param startDate
     * @param endDate
     * @return
     */
    public static String getDatSpanMin(Date endDate,Date startDate){
    	long between = endDate.getTime()-startDate.getTime();
    	long day = between / (24 * 60 * 60 * 1000);
        long hour = (between / (60 * 60 * 1000) - day * 24);
        long min = ((between / (60 * 1000)) - day * 24 * 60 - hour * 60);
        /*long s = (between / 1000 - day * 24 * 60 * 60 - hour * 60 * 60 - min * 60);
        long ms = (between - day * 24 * 60 * 60 * 1000 - hour * 60 * 60 * 1000
                - min * 60 * 1000 - s * 1000);*/
        String result = hour + "小时," + min + "分。";
    	return result;
    }
	/**
	 * 得到两个日期相差的分钟
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	public static long getMinutes(Date endDate,Date startDate){
		long between = endDate.getTime()-startDate.getTime();
		long min = ((between / (60 * 1000)));
		return min;
	}

	//	获取当前时间的零点或者24点getNeedTime(23,59,59,0);
	//	可以定义一个函数，函数的参数有小时、分、秒、相比今天的日期，今天就输入0，明天输入1，昨天输入-1，以此类推
	//	(毫秒是可选参数，可以输入也可以不输入，毫秒的取值范围是0到999)
	public static Date getNeedTime(int hour, int minute, int second, int addDay, int... args){
		Calendar calendar = Calendar.getInstance();
		if(addDay != 0){
			calendar.add(Calendar.DATE,addDay);
		}
		calendar.set(Calendar.HOUR_OF_DAY,hour);
		calendar.set(Calendar.MINUTE,minute);
		calendar.set(Calendar.SECOND,second);
		if(args.length==1){
			calendar.set(Calendar.MILLISECOND,args[0]);
		}
		return calendar.getTime();
	}
}
