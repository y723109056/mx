package com.mx.controller;

import com.mx.aspect.ActionTime;
import com.mx.dao.CityInfoMapper;
import com.mx.entity.User;
import com.mx.entity.ip.IPQuery;
import com.mx.entity.ip.IPResult;
import com.mx.entity.weather.IWeatherQuery;
import com.mx.entity.weather.W_1_Result;
import com.mx.services.IUserService;
import com.mx.services.IWeatherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

/**
 * @author 小米线儿
 * @time 2019/2/12 0012
 * @QQ 723109056
 * @blog https://blog.csdn.net/qq_31407255
 */

@Controller
public class HelloController extends BaseController {



    @Autowired
    private IUserService userService;

    @Autowired
    private IWeatherService weatherService;

    @Autowired
    private CityInfoMapper cityInfoMapper;


    @RequestMapping("/test")
    @ActionTime(name="test测试")
    public String test(){

        System.out.println("test...");
        if(System.currentTimeMillis()>0){
            throw new NullPointerException("测试空指针");
        }

        return "hello";
    }

    @RequestMapping("/user")
    @ResponseBody
    public User test1(Integer id){

        User user = userService.selectUserByUserId(id);

        return user;
    }


    @RequestMapping("/testIp")
    @ResponseBody
    public String testIp(){

        IPQuery query = new IPResult();

        String cityId = cityInfoMapper.queryCityIdByCityName(query.getCity());

        Map<String,String> map = new HashMap<String,String>();

        map.put("app","360chrome");
        map.put("code",cityId);

        IWeatherQuery weatherQuery = new W_1_Result(map);

        String str = weatherQuery.getWeather();


        return weatherQuery.getWeather();
    }

}
