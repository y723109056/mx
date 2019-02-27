package com.mx.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mx.entity.User;
import com.mx.services.IUserService;
import com.mx.util.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

/**
 * @author 小米线儿
 * @time 2019/2/23 0023
 * @QQ 723109056
 * @blog https://blog.csdn.net/qq_31407255
 */


@Controller
@RequestMapping(("/register"))
public class RegisterController extends BaseController {

    @Autowired
    IUserService userService;

    private final static Logger LOG = LoggerFactory.getLogger(RegisterController.class);


    /**
     * 注册
     * @param user
     * @return
     */
    @RequestMapping(value = "/doRegister",method = RequestMethod.POST)
    @ResponseBody
    public String doRegister(User user) {

        LOG.info(user.toString());

        try {
            userService.addUser(user);
            return renderText(true,"注册成功",null);
        }catch (Exception e){
            LOG.error(e.getMessage());
            return renderText(false,"注册失败",e.getMessage());
        }
    }

    /**
     * 检查userName OR email是否可用
     * @param user
     * @return
     */
    @RequestMapping(value = "/checkUser",produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String checkUser(User user) {
        LOG.info(user.toString());

        String resultString = "";
        boolean result = false;
        if(!(StringUtil.isNullOrEmpty(user.getUserName())&&StringUtil.isNullOrEmpty(user.getEmail()))){
            result = userService.checkUser(user);
        }
        Map<String, Boolean> map = new HashMap<>();
        map.put("valid",result);
        ObjectMapper mapper = new ObjectMapper();

        try {
            resultString = mapper.writeValueAsString(map);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return resultString;
    }


}
