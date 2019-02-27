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
public class IPAreaCoordResult extends AbstractIpQuery {
    public IPAreaCoordResult(){
        super(null);
    }

    public IPAreaCoordResult(Map params) {
        super(params);
    }

    @Override
    public RequestMethod getRequestMethod() {
        return IpQueryUrlEnum.IP_AREA_COORD.method;
    }

    @Override
    public String getUrl() {
        return IpQueryUrlEnum.IP_AREA_COORD.url;
    }

    @Override
    public String getCharset() {
        return IpQueryUrlEnum.IP_AREA_COORD.encoding.name;
    }

    @Override
    public Boolean isGzip() {
        return IpQueryUrlEnum.IP_AREA_COORD.isGzip;
    }

    @Override
    public Result parse(String result) {
        return null;
    }
}
