package com.mx.servlet;

import net.sf.json.JSONObject;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 类解释
 *
 */
public class ServletUtil {

    private ServletUtil(){}

    private static HttpServletResponse getResponse(){
        return ServletContextHolder.getResponse();
    }

    /**
     * 判断是否为ajax请求
     *
     * @return
     */
    public static boolean isAjax(){
        return isAjax(ServletContextHolder.getRequest());
    }

    /**
     * 判断是否为ajax请求
     *
     * @param request
     * @return
     */
    public static boolean isAjax(HttpServletRequest request){
        String requestType = request.getHeader("X-Requested-With");
        if (requestType != null && requestType.equals("XMLHttpRequest")) {
            return true;
        } else {
            return false;
        }
    }

    public static String render(String text, String contentType) {
        try {
            HttpServletResponse response = getResponse();
            response.setContentType(contentType);
            //response.setCharacterEncoding("UTF-8");
            response.getWriter().write(text);
        } catch (IOException e) {
        }
        return null;
    }

    public static String render(String text, String contentType,Integer status) {
        try {
            HttpServletResponse response = getResponse();
            response.setContentType(contentType);
            response.setStatus(status);
            //response.setCharacterEncoding("UTF-8");
            response.getWriter().write(text);
        } catch (IOException e) {
        }
        return null;
    }

    public static String renderText(String text) {
        return render(text, "text/plain;charset=UTF-8");
    }

    public static String renderText(String text,Integer status) {
        return render(text, "text/plain;charset=UTF-8", status);
    }

    /**
     * 给前端页面发送json字符串
     * @param success 请求是否成功
     * @param cause	  原因
     * @param value  具体的值
     * @return
     */
    public static String renderText(boolean success,String cause,String value){
        JSONObject json = new JSONObject();
        json.put("success", success);
        json.put("cause", cause);
        json.put("value", value);
        return render(json.toString(),"text/plain;charset=UTF-8");
    }

}
