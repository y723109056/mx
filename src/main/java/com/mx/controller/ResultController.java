package com.mx.controller;

import com.mx.exception.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author 小米线儿
 * @time 2019/2/13 0013
 * @QQ 723109056
 * @blog https://blog.csdn.net/qq_31407255
 */

@Controller
@RequestMapping("/result")
public class ResultController {


    @Autowired
    private ExceptionHandle exceptionHandle;

    /**
     * 返回体测试
     * @param num
     * @return
     */
    @RequestMapping(value = "/getResult",method = RequestMethod.GET)
    @ResponseBody
    public Result getResult(@RequestParam("num") Integer num){
        Result result = null;
        try {
            switch (num){
                case 0:
                    result = ResultUtil.success();
                    break;
                case -1:
                    result = ResultUtil.error();
                    break;
                default :
                    throw new UnKnowException(num,"参数不对");
            }

        }catch (Exception e){
            result =  exceptionHandle.exceptionGet(e);
        }
        return result;
    }



}
