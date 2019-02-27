package com.mx.controller;

import com.mx.entity.User;
import com.mx.enums.IndexBgGifEnum;
import com.mx.enums.ResponseCodeEnum;
import com.mx.services.IUserService;
import com.mx.services.IWeatherService;
import com.mx.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.Random;

/**
 * @author 小米线儿
 * @time 2019/2/21 0021
 * @QQ 723109056
 * @blog https://blog.csdn.net/qq_31407255
 */

@Controller
@RequestMapping("/login")
public class LoginController extends BaseController{

    @Autowired
    private IWeatherService weatherService;

    @Autowired
    private IUserService userService;

    @RequestMapping("/getLoginPage")
    public String getLoginPage(HttpServletRequest request){

        String gif = "";
        //先查天气，根据天气情况匹配选择相应背景
        try{
//            weatherService.getWeather();

            //TO DO
            throw new RuntimeException("解析天气接口待完成...");

        }catch (Exception e){
            //接口不通就路由到随机背景
            gif = IndexBgGifEnum.valueOfCode(new Random().nextInt(4)).gif;
        }
        request.setAttribute("bg",gif);
        return "login/login";
    }

    @RequestMapping("/register")
    public String register(){
        return "login/register";
    }

    @RequestMapping("/getGamesPage")
    public String getGamesPage(){
        return "login/games";
    }


    @RequestMapping("/doLogin")
    @ResponseBody
    public String login(User user){
        //验证邮箱和密码
        if(user!=null){
            if(StringUtil.isNotEmpty(user.getEmail())&&StringUtil.isNotEmpty(user.getPassword())){
                User query = userService.doLogin(user);
                if(query!=null){
                    return success();
                }
                return fail(ResponseCodeEnum.CODE_104);
            }else{
                return fail(ResponseCodeEnum.CODE_102);
            }
        }else{
            return fail(ResponseCodeEnum.CODE_105);
        }
    }


}
