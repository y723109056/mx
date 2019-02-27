package com.mx.entity.weather.abstr;

import com.mx.entity.IHttpRequest;
import com.mx.entity.weather.IWeatherQuery;

import java.util.Map;

/**
 * @author 小米线儿
 * @time 2019/2/25 0025
 * @QQ 723109056
 * @blog https://blog.csdn.net/qq_31407255
 */
public abstract class AbstractWeatherQuery extends IHttpRequest implements IWeatherQuery {

    public AbstractWeatherQuery(Map params) {
        super(params);
    }

    public String getWeather(){
        Result r = (Result) result;
        return r.weather;
    }

    public class Result{

        String weather;

        public Result(String weather) {
            this.weather = weather;
        }
    }
}
