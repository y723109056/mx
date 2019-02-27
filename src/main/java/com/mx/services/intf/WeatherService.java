package com.mx.services.intf;

import com.mx.dao.CityInfoMapper;
import com.mx.entity.ip.IPQuery;
import com.mx.entity.ip.IPResult;
import com.mx.entity.weather.IWeatherQuery;
import com.mx.entity.weather.W_1_Result;
import com.mx.services.IWeatherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * @author 小米线儿
 * @time 2019/2/24 0024
 * @QQ 723109056
 * @blog https://blog.csdn.net/qq_31407255
 */

@Service
public class WeatherService implements IWeatherService{


    @Autowired
    CityInfoMapper cityInfoMapper;

    @Override
    public String getWeather() {
        try {
            IPQuery query = new IPResult();
            String cityId = cityInfoMapper.queryCityIdByCityName(query.getCity());
            Map<String,String> map = new HashMap<String,String>();

            map.put("app","360chrome");
            map.put("code",cityId);

            IWeatherQuery weatherQuery = new W_1_Result(map);

            String str = weatherQuery.getWeather();
            return str;
        }catch (Exception e){
            return null;
        }

    }
}
