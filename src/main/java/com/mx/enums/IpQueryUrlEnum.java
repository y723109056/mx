package com.mx.enums;

/**
 * @author 小米线儿
 * @time 2019/2/25 0025
 * @QQ 723109056
 * @blog https://blog.csdn.net/qq_31407255
 */

import org.springframework.web.bind.annotation.RequestMethod;

/**
 * 查询ip枚举
 */
public enum IpQueryUrlEnum {

    IP("http://whois.pconline.com.cn/ip.jsp",RequestMethod.POST, Charset.GBK,Boolean.FALSE),
    IP_JSON("http://whois.pconline.com.cn/ipJson.jsp",RequestMethod.POST,Charset.GBK,Boolean.FALSE),
    JS_ALERT("http://whois.pconline.com.cn/jsAlert.jsp",RequestMethod.POST,Charset.GBK,Boolean.FALSE),
    JS_DOM("http://whois.pconline.com.cn/jsDom.jsp",RequestMethod.POST,Charset.GBK,Boolean.FALSE),
    JS_FUNCTION("http://whois.pconline.com.cn/jsFunction.jsp",RequestMethod.POST,Charset.GBK,Boolean.FALSE),
    JS_LABLE("http://whois.pconline.com.cn/jsLable.jsp",RequestMethod.POST,Charset.GBK,Boolean.FALSE),
    JS_WRITE("http://whois.pconline.com.cn/jsWrite.jsp",RequestMethod.POST,Charset.GBK,Boolean.FALSE),
    WHOI("http://whois.pconline.com.cn/whois/index.jsp",RequestMethod.POST,Charset.GBK,Boolean.FALSE),
    IP_AREA_COORD("http://whois.pconline.com.cn/ipAreaCoord.jsp?coords=",RequestMethod.GET,Charset.GBK,Boolean.FALSE),//coords=latitude,longitude
    IP_AREA_COORD_JSON("http://whois.pconline.com.cn/ipAreaCoordJson.jsp?coords=",RequestMethod.GET,Charset.GBK,Boolean.FALSE);//coords=latitude,longitude

    public final String url;

    public final RequestMethod method;

    public final Charset encoding;

    public final Boolean isGzip;

    IpQueryUrlEnum(String url, RequestMethod method, Charset encoding, Boolean isGzip) {
        this.url = url;
        this.method = method;
        this.encoding = encoding;
        this.isGzip = isGzip;
    }
}
