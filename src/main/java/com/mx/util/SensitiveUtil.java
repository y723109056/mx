package com.mx.util;

import org.apache.commons.lang.StringUtils;

/**
 * 脱敏
 * Created by zhusw on 2017/7/5.
 */
public class SensitiveUtil {

    /**
     * 脱敏
     * @param str
     * @param preLen
     * @param lastlen
     * @return
     */
    public static String getTakeOffSensitiveMiddle(String str,int preLen,int lastlen){
        if(StringUtils.isBlank(str))
            return "";
        String temp = str;
        int len = str.length();
        StringBuffer sb = new StringBuffer();
        for(int i=0;i<len;i++){
            if(i>preLen && i<(len-lastlen)){
                sb.append("*");
            }else {
                sb.append(str.charAt(i));
            }
        }
        return sb.toString();
    }

    /**
     * 脱敏
     * @param str
     * @param lastLen 最后几位脱敏
     * @return
     */
    public static String getTakeOffSensitiveLast(String str,int lastLen){
        if(StringUtils.isBlank(str))
            return "";
        String temp = str;
        int len = str.length();
        StringBuffer sb = new StringBuffer();
        for(int i=0;i<len;i++){
            if(i>=(len-lastLen)){
                sb.append("*");
            }else {
                sb.append(str.charAt(i));
            }
        }
        return sb.toString();
    }

}
