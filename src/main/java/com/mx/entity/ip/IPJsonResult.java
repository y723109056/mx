package com.mx.entity.ip;

import com.mx.entity.ip.abstr.AbstractIpQuery;
import com.mx.enums.IpQueryUrlEnum;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.script.ScriptEngineManager;
import java.util.Map;

/**
 * @author 小米线儿
 * @time 2019/2/25 0025
 * @QQ 723109056
 * @blog https://blog.csdn.net/qq_31407255
 */
public class IPJsonResult extends AbstractIpQuery {

    public IPJsonResult(){
        super(null);
    }
    public IPJsonResult(Map params) {
        super(params);
    }

    @Override
    public RequestMethod getRequestMethod() {
        return IpQueryUrlEnum.IP_JSON.method ;
    }
    @Override
    public String getUrl() {
        return IpQueryUrlEnum.IP_JSON.url;
    }

    @Override
    public String getCharset() {
        return IpQueryUrlEnum.IP_JSON.encoding.name;
    }

    @Override
    public Boolean isGzip() {
        return IpQueryUrlEnum.IP_JSON.isGzip;
    }

    @Override
    public Result parse(String result) {

        //TO DO
        return null;
    }

    public static void main(String[] args) {
        String jsStr = "if(window.IPCallBack) {IPCallBack({\"ip\":\"112.10.230.5\",\"pro\":\"浙江省\",\"proCode\":\"330000\",\"city\":\"杭州市\",\"cityCode\":\"330100\",\"region\":\"\",\"regionCode\":\"0\",\"addr\":\"浙江省杭州市 移通\",\"regionNames\":\"\",\"err\":\"\"});}";

        ScriptEngineManager sem=new ScriptEngineManager();




    }

}
