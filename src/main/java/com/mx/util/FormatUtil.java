package com.mx.util;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

/**
 * Created by Administrator on 14-3-11.
 */
public class FormatUtil {

	public static String formatDateToStr(String df,Date date){
		if(date == null){
			return "";
		}else{
			SimpleDateFormat dateFormat = new SimpleDateFormat(df);
			return dateFormat.format(date);
		}
	}
	
	public static String formatHHDateToStr(Date date){
		return formatDateToStr("yyyy-MM-dd HH:mm:ss",date);
	}

	public static String formatDateToStr(Date date){
		return formatDateToStr("yyyy-MM-dd",date);
	}
	
	public static String formatDate(Object obj,String format){
		if(obj instanceof Date){
			return formatDateToStr(format,(Date)obj);
		}else if(obj instanceof String){
			return (String)obj;
		}
		return "";
	}
	
	public static String formatTime(Object obj,String format){
		if(obj instanceof Date){
			return formatDateToStr(format,(Date)obj);
		}else if(obj instanceof Integer){
			int num = (Integer)obj;
			int hour = num / 3600;
			int minute = num %3600 / 60;
			int second = num % 60;
			return StringUtil.padLeft(hour+"", 2, '0')+":"+StringUtil.padLeft(minute+"", 2, '0')+":"+StringUtil.padLeft(second+"", 2, '0');
		}
		return "00:00:00";
	}
	
    /**
     * 转换文件大小
     * @param size
     * @return
     */
    public static String formetFileSize(String size) {
        DecimalFormat df = new DecimalFormat("#.00");
        String fileSizeString = "";
        int fileS = Integer.parseInt(size); 
        if (fileS < 1024) {
            fileSizeString = df.format((double) fileS) + "B";
        } else if (fileS < 1048576) {
            fileSizeString = df.format((double) fileS / 1024) + "Kb";
        } else if (fileS < 1073741824) {
            fileSizeString = df.format((double) fileS / 1048576) + "MB";
        } else {
            fileSizeString = df.format((double) fileS / 1073741824) + "G";
        }
        return fileSizeString;
    }
    
    /**
     * 
     * @param map
     * @param params
     */
    public static void formatDateToMap(Map<String,Object> map,String params){
    	formatDateToMap(map,params.split("\\,"));
    }
    
    /**
     * 格式化Map对像里的日期
     * @param map
     * @param params
     */
    public static void formatDateToMap(Map<String,Object> map,String[] params){
    	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    	for(String param : params){
    		Object value = map.get(param);
    		if(value instanceof String){
    			try {
					Date date = sdf.parse(value.toString());
					map.put(param, date);
				} catch (ParseException e) {
					e.printStackTrace();
				}
    		}
    	}
    }

	/**
	 * 格式化金额
	 * @param amount
	 * @return
	 */
	public static String formatDecimal(BigDecimal amount){
		if(amount!=null){
			double d = amount.doubleValue();
			if((d-(long)d)>0) {
				return new DecimalFormat(",###.##").format(amount);
			}else{
				return new DecimalFormat(",###").format(amount);
			}
		}
		return "";
	}

	/**
	 * 格式化金额
	 * @param amount
	 * @return
	 */
	public static String formatDecimal(BigDecimal amount,String format){
		if(amount!=null){
			return new DecimalFormat(format).format(amount);
		}
		return "";
	}
	public static void main(String[] args) {
		System.out.println(formatDecimal(new BigDecimal("-22.222")));
		System.out.println(formatDecimal(new BigDecimal("-222222.22"),".#"));
	}
}
