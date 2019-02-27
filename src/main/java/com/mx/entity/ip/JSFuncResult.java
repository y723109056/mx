package com.mx.entity.ip;

import com.mx.entity.ip.abstr.AbstractIpQuery;
import com.mx.enums.IpQueryUrlEnum;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.Map;

/**
 * @author 小米线儿
 * @time 2019/2/25 0025
 * @QQ 723109056
 * @blog https://blog.csdn.net/qq_31407255
 */
public class JSFuncResult extends AbstractIpQuery {

    public JSFuncResult(){
        super(null);
    }

    public JSFuncResult(Map params) {
        super(params);
    }

    @Override
    public RequestMethod getRequestMethod() {
        return IpQueryUrlEnum.JS_FUNCTION.method;
    }

    @Override
    public String getUrl() {
        return IpQueryUrlEnum.JS_FUNCTION.url;
    }

    @Override
    public String getCharset() {
        return IpQueryUrlEnum.JS_FUNCTION.encoding.name;
    }

    @Override
    public Boolean isGzip() {
        return null;
    }

    @Override
    public Result parse(String result) {
        return null;
    }
}
