package com.mx.util;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class VmHelper {
	
	public static boolean isContains(String a,String b,String reg){
		if(a == null || "".equals(a) || b == null || "".equals(b)){
			return false;
		}
		String[] arr = a.split(reg);
		for(String s : arr){
			if(s.equalsIgnoreCase(b)){
				return true;
			}
		}
		return false;
	}

    /**
     * 统一处理金额保留两位小数点
     *
     * @param money
     * @return
     */
    public static String getUnityDecimalMoney(BigDecimal money,int maxSuffix,int minSuffix){
        if (money == null){
            money = new BigDecimal(0);
        }
        NumberFormat nf = NumberFormat.getNumberInstance(Locale.US);
        nf.setMaximumFractionDigits(maxSuffix);
        nf.setMinimumFractionDigits(minSuffix);
        String ret = nf.format(money);
        return ret.replaceAll(",","");
    }

    /**
     *
     * @param money 金额值
     * @param unit 单位(元、万元等)0为元、1为万元
     * @param scale 小数点位数
     * @param roundingMode 四舍五入
     * @return
     */
    public static BigDecimal getDecimalByUnit(BigDecimal money,int unit,
                                              int scale,int roundingMode){
        if (money == null){
            money = new BigDecimal(0);
        }
        if (unit==1){
             //万元单位
            money = money.divide(new BigDecimal(10000),scale,roundingMode);
        }
        return money;
    }

    /**
     * 将秒转化为时分秒显示
     * @param sec
     * @return
     */
    public String secToTime(Integer sec){
        int h=(int)(sec/3600%24);
        int m=(int)(sec/60%60);
        int s=(int)(sec%60);
        return ((h > 9) ? h : "0" + h) + "时:" + ((m > 9) ? m : "0" + m) + "分:" + ((s > 9) ? s : "0" + s)+"秒";
    }

    /**
     * 将时间类型转换为字符类型
     * @param date
     * @return
     */
    public String DateToString(Date date){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        if(date!=null){
            String formatDate = sdf.format(date);
            return formatDate;
        }else{
            return null;
        }
    }
}
