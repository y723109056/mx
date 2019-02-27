package com.mx.entity.weather;

import org.springframework.web.bind.annotation.RequestMethod;

import java.util.Map;

/**
 * @author 小米线儿
 * @time 2019/2/25 0025
 * @QQ 723109056
 * @blog https://blog.csdn.net/qq_31407255
 */
public interface IWeatherQuery {

    public String getWeather();

    public String getUrl();

    public String getCharset();

    public Map getParams();

    public RequestMethod getRequestMethod() ;


}
