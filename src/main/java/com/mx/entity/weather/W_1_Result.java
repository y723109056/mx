package com.mx.entity.weather;

import com.mx.entity.weather.abstr.AbstractWeatherQuery;
import com.mx.enums.WeatherQueryUrlEnum;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.Map;

/**
 * @author 小米线儿
 * @time 2019/2/25 0025
 * @QQ 723109056
 * @blog https://blog.csdn.net/qq_31407255
 */
public class W_1_Result extends AbstractWeatherQuery {

    public W_1_Result(Map params) {
        super(params);
    }

    @Override
    public String getUrl() {
        return WeatherQueryUrlEnum.W_1.url;
    }

    @Override
    public String getCharset() {
        return WeatherQueryUrlEnum.W_1.encoding.name;
    }

    @Override
    public Boolean isGzip() {
        return WeatherQueryUrlEnum.W_1.isGzip;
    }

    @Override
    public RequestMethod getRequestMethod() {
        return WeatherQueryUrlEnum.W_1.method;
    }

    @Override
    public Object parse(String resultMsg) {
        return new Result(resultMsg);
    }
}
