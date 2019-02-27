package com.mx.util;

import java.math.BigDecimal;

public class MathUtil {
	/**
	 * 两个数做加法
	 * @param num1
	 * @param num2
	 * @return
	 */
	public static Object add(Object num1,Object num2){
		if(num1!=null && num2!=null){
			if(num1 instanceof BigDecimal || num2 instanceof BigDecimal){
				BigDecimal n1 = (BigDecimal)TypeUtil.changeType(num1, BigDecimal.class);
				BigDecimal n2 = (BigDecimal)TypeUtil.changeType(num2, BigDecimal.class);
				return n1.add(n2);
			}else if(num1 instanceof Double || num2 instanceof Double){
				Double n1 = (Double)TypeUtil.changeType(num1, Double.class);
				Double n2 = (Double)TypeUtil.changeType(num2, Double.class);
				return n1+n2;
			}else if(num1 instanceof Long || num2 instanceof Long){
				Long n1 = (Long)TypeUtil.changeType(num1, Long.class);
				Long n2 = (Long)TypeUtil.changeType(num2, Long.class);
				return n1+n2;
			}else if(num1 instanceof Integer || num2 instanceof Integer){
				Integer n1 = (Integer)TypeUtil.changeType(num1, Integer.class);
				Integer n2 = (Integer)TypeUtil.changeType(num2, Integer.class);
				return n1+n2;
			}
		}else if(num1!=null){
			return num1;
		}else if(num2!=null){
			return num2;
		}
		return null;
	}
	
	/**
	 * 取平均数量
	 * @param num1
	 * @param count
	 * @return
	 */
	public static Object average(Object num,Integer count){
		if(num instanceof BigDecimal){
			return ((BigDecimal)num).divide(new BigDecimal(count));
		}else if(num instanceof Double){
			return (new BigDecimal((Double)num)).divide(new BigDecimal(count));
		}else if(num instanceof Long){
			return (new BigDecimal((Long)num)).divide(new BigDecimal(count));
		}else if(num instanceof Integer){
			return (new BigDecimal((Integer)num)).divide(new BigDecimal(count));
		}
		return null;
	}
	
	/**
	 * 
	 * @param num
	 * @param count
	 * @param scale
	 * @return
	 */
	public static Object average(Object num,Integer count,int scale){
		return average(num,count,scale,BigDecimal.ROUND_UP);
	}
	
	/**
	 * 取平均数量
	 * @param num
	 * @param count
	 * @param scale
	 * @param mode
	 * @return
	 */
	public static Object average(Object num,Integer count,int scale,int mode){
		if(num instanceof BigDecimal){
			return ((BigDecimal)num).divide(new BigDecimal(count),scale,mode);
		}else if(num instanceof Double){
			return (new BigDecimal((Double)num)).divide(new BigDecimal(count),scale,mode);
		}else if(num instanceof Long){
			return (new BigDecimal((Long)num)).divide(new BigDecimal(count),scale,mode);
		}else if(num instanceof Integer){
			return (new BigDecimal((Integer)num)).divide(new BigDecimal(count),scale,mode);
		}
		return null;
	}
}
