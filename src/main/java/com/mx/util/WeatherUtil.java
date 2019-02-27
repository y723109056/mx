package com.mx.util;

import com.mx.enums.Charset;

import java.util.HashMap;

/**
 * @author 小米线儿
 * @time 2019/2/24 0024
 * @QQ 723109056
 * @blog https://blog.csdn.net/qq_31407255
 */
public class WeatherUtil {

//    private final static String weatherUrl_1 = "http://www.weather.com.cn/data/sk/";

    private final static String weatherUrl_2 ="http://www.weather.com.cn/data/cityinfo/";

    private final static String ipUrl = "http://whois.pconline.com.cn/ip.jsp";

    private final static String suffix = ".html";

    public static String getWeather(Integer cityId){

        String path = weatherUrl_2 + cityId + suffix;

//        String resultMsg = HttpUtil.doPost(path);
        String resultMsg = HttpUtils.sendPost(path,new HashMap(), Charset.GBK.name);

//        if(!ApiJsonUtil.isJson(resultMsg)){
//            path = weatherUrl_2 + cityId + suffix;
//            resultMsg = HttpUtil.doPost(path);
//        }

        return resultMsg;

    }

    public static String getCityName(){
        return HttpUtil.doPost(ipUrl);
    }

}
