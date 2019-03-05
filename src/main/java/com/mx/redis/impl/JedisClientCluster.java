package com.mx.redis.impl;

import com.mx.redis.JedisClient;
import org.springframework.beans.factory.annotation.Autowired;
import redis.clients.jedis.JedisCluster;

/**
 * @author 小米线儿
 * @time 2019/3/3 0003
 * @QQ 723109056
 * @blog https://blog.csdn.net/qq_31407255
 */
public class JedisClientCluster implements JedisClient {
    @Autowired
    private JedisCluster jedisCluster;

    @Override
    public String set(String key, String value) {
        return jedisCluster.set(key, value);
    }

    @Override
    public String get(String key) {
        return jedisCluster.get(key);
    }

    @Override
    public Long hset(String key, String item, String value) {
        return jedisCluster.hset(key, item, value);
    }

    @Override
    public String hget(String key, String item) {
        return jedisCluster.hget(key, item);
    }

    @Override
    public Long incr(String key) {
        return jedisCluster.incr(key);
    }

    @Override
    public Long decr(String key) {
        return jedisCluster.decr(key);
    }

    @Override
    public Long expire(String key, int second) {
        return jedisCluster.expire(key, second);
    }

    @Override
    public Long ttl(String key) {
        return jedisCluster.ttl(key);
    }
}
