package com.mx.controller;

import com.mx.common.AccessInfoHolder;
import com.mx.enums.ResponseCodeEnum;
import com.mx.permission.ILoginInfo;
import com.mx.servlet.ServletContextHolder;
import com.mx.util.StringUtil;
import com.mx.util.TypeUtil;
import com.mx.util.XSSFilterUtil;
import net.sf.json.JSONObject;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.Serializable;

/**
 * @author 小米线儿
 * @time 2019/2/23 0023
 * @QQ 723109056
 * @blog https://blog.csdn.net/qq_31407255
 */
public abstract class BaseController implements Serializable {


    private static final long serialVersionUID = 459585445145679117L;


    /**
     * 给前端页面发送json字符串
     * @param success 请求是否成功
     * @param cause	  原因
     * @param data  具体的值
     * @return
     */
    public String renderText(boolean success,String cause,Object data){
        JSONObject json = new JSONObject();
        json.put("success", success);
        json.put("cause", cause);
        json.put("data", data);
        return json.toString();
    }

    public String fail(ResponseCodeEnum codeEnum){
        JSONObject json = new JSONObject();
        json.put("success", false);
        json.put("cause", codeEnum.getMsg());
        json.put("data", codeEnum.getCode());
        return json.toString();
    }

    public String success(){
        JSONObject json = new JSONObject();
        json.put("success", true);
        json.put("cause", ResponseCodeEnum.CODE_100.getMsg());
        json.put("data",  ResponseCodeEnum.CODE_100.getCode());
        return json.toString();
    }




    public String renderText(String text) {
        this.getResponse().setHeader("result", "success".equals(text)?"success":"fail");		//token拦截器用到
        return render(text, "text/plain;charset=UTF-8");
    }
    /**
     * 获得注入的bean对象
     * @param name
     * @return
     */
    public Object getBean(String name){

        WebApplicationContext container  = WebApplicationContextUtils.getWebApplicationContext(ServletContextHolder.getSession().getServletContext());
        if(container.containsBean(name)){
            return container.getBean(name);
        }
        return null;
    }

    /**
     * 判断提交方式是否是ajax
     */
    public boolean isAjax() {
        String requestType = getRequest().getHeader("X-Requested-With");
        if (requestType != null && requestType.equals("XMLHttpRequest")) {
            return true;
        } else {
            return false;
        }
    }

    protected String render(String text, String contentType) {
        try {
            HttpServletResponse response = getResponse();
            response.setContentType(contentType);
            response.setCharacterEncoding("UTF-8");
            response.getWriter().write(text);
        } catch (IOException e) {
        }
        return null;
    }


    public String renderHtml(String html) {
        return render(html, "text/html;charset=UTF-8");
    }

    public String renderXML(String xml) {
        return render(xml, "text/xml;charset=UTF-8");
    }

    public HttpServletRequest getRequest() {
        return ServletContextHolder.getRequest();
    }

    public HttpServletResponse getResponse() {
        return ServletContextHolder.getResponse();
    }

    public HttpSession getSession() {
        return getRequest().getSession();
    }

    public String getParameter(String param){
        return getParameter(param,null);
    }

    public String getParameter(String param,String defulatVavlue){
        return getParameter(param,null,null,null);
    }

    public String getParameter(String param,String defulatVavlue,Integer maxLength){
        return getParameter(param,null,maxLength,null);
    }

    public String getParameter(String param,String defulatVavlue,Class<?> clazz){
        return getParameter(param,null,null,clazz);
    }

    public String getParameter(String param,String defulatVavlue,Integer maxLength,Class<?> clazz){
        String str = this.getRequest().getParameter(param);
        if(str!=null){
            if(maxLength!=null && str.length()>maxLength){
                return str.substring(0, maxLength);
            }
            if(StringUtil.isNotEmpty(str) && clazz!=null){
                try{
                    TypeUtil.changeType(str, clazz);
                }catch(Exception e){
                    throw new RuntimeException("参数"+param+"类型格式不正确:"+str);
                }
            }
            if(StringUtil.isNotEmpty(str) && !XSSFilterUtil.isJsonString(str)){
                return XSSFilterUtil.cleanXSS(str);
            }
        }else{
            return defulatVavlue;
        }
        return str;
    }

    public void setAttribute(String name,Object value){
        this.getRequest().setAttribute(name, value);
    }

    /**
     *
     * @return
     */
    public ILoginInfo getLoginInfo(){
        return AccessInfoHolder.getWebLoginInfo();
    }




}
