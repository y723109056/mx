package com.mx.redis;

/**
 * @author 小米线儿
 * @time 2019/3/3 0003
 * @QQ 723109056
 * @blog https://blog.csdn.net/qq_31407255
 */
public interface JedisClient {

    public String set(String key, String value);
    public String get(String key);
    public Long hset(String key, String item, String value);
    public String hget(String key, String item);
    public Long incr(String key);
    public Long decr(String key);
    public Long expire(String key, int second);
    public Long ttl(String key);

}
