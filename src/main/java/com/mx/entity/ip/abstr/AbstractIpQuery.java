package com.mx.entity.ip.abstr;

import com.mx.entity.IHttpRequest;
import com.mx.entity.ip.IPQuery;

import java.util.Map;

/**
 * @author 小米线儿
 * @time 2019/2/25 0025
 * @QQ 723109056
 * @blog https://blog.csdn.net/qq_31407255
 */
public abstract class AbstractIpQuery extends IHttpRequest implements IPQuery {

    public AbstractIpQuery(Map params) {
        super(params);
    }
    public  String  getIP(){
        Result r = (Result) result;
        return r.getIp();
    }

    public String getCity(){
        Result r = (Result) result;
        return r.getCity();
    }

    public class Result{

        private String ip;
        private String city;

        public Result(String ip, String city) {
            this.ip = ip;
            this.city = city;
        }

        public String getIp() {
            return ip;
        }

        public void setIp(String ip) {
            this.ip = ip;
        }

        public String getCity() {
            return city;
        }

        public void setCity(String city) {
            this.city = city;
        }
    }

}
