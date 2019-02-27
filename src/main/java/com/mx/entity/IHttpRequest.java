package com.mx.entity;

import com.mx.exception.IPQueryException;
import com.mx.util.GZIPUtil;
import com.mx.util.HttpUtils;
import com.mx.util.UnicodeBackslashU;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.Map;

/**
 * @author 小米线儿
 * @time 2019/2/25 0025
 * @QQ 723109056
 * @blog https://blog.csdn.net/qq_31407255
 */
public abstract class IHttpRequest {

    public final Map params;

    public final Object result;

    public IHttpRequest(Map params) {
        this.params = params;
        result = parseResult();
    }

    public Map getParams(){
        return params;
    }

    public abstract String getUrl();

    public abstract String getCharset();

    public abstract Boolean isGzip();

    public abstract RequestMethod getRequestMethod() ;

    public abstract Object parse(String resultMsg);

    public Object parseResult(){
        String resultMsg = "";
        switch (getRequestMethod()){
            case GET:
                resultMsg = HttpUtils.sendGet(getUrl(), getParams(), getCharset());
                break;
            case POST:
                resultMsg = HttpUtils.sendPost(getUrl(),getParams(),getCharset());
                break;
            default:
                throw new IPQueryException("unspport this method :"+getRequestMethod().name());
        }
        if(isGzip()){
            resultMsg = GZIPUtil.unGZip(resultMsg);
        }
        resultMsg = UnicodeBackslashU.unicodeToCn(resultMsg);
        Object result = parse(resultMsg);
        return result;
    }

}
