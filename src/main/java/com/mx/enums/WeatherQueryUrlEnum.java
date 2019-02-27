package com.mx.enums;

/**
 * @author 小米线儿
 * @time 2019/2/25 0025
 * @QQ 723109056
 * @blog https://blog.csdn.net/qq_31407255
 */

import org.springframework.web.bind.annotation.RequestMethod;

/**
 * 查询天气的枚举
 */
public enum WeatherQueryUrlEnum {

    W_1("http://cdn.weather.hao.360.cn/sed_api_weather_info.php" //app=360chrome&code=
            ,"360的天气接口,返回的是Json格式",RequestMethod.GET,Charset.GBK,Boolean.TRUE),
    W_2("http://www.weather.com.cn/data/cityinfo/","中国气象局提供的天气接口",RequestMethod.POST,Charset.GBK,Boolean.FALSE),
    W_3("http://www.weather.com.cn/data/sk/","中国气象局提供的天气接口",RequestMethod.POST,Charset.GBK,Boolean.FALSE);

    public String url;

    public String info;

    public RequestMethod method;

    public Charset encoding;

    public Boolean isGzip;

    WeatherQueryUrlEnum(String url, String info, RequestMethod method, Charset encoding, Boolean isGzip) {
        this.url = url;
        this.info = info;
        this.method = method;
        this.encoding = encoding;
        this.isGzip = isGzip;
    }
}
