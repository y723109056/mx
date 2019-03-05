package com.mx.redis;

import org.junit.Test;
import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisCluster;
import redis.clients.jedis.JedisPool;

import java.util.HashSet;
import java.util.Set;

/**
 * @author 小米线儿
 * @time 2019/3/3 0003
 * @QQ 723109056
 * @blog https://blog.csdn.net/qq_31407255
 */
public class JedisTest {

    @Test
    public void testJedisSingle() throws Exception {
        //创建一个Jedis对象
        Jedis jedis = new Jedis("192.168.81.120", 6379);
        jedis.set("test", "hello jedis");
        String string = jedis.get("test");
        System.out.println(string);
        jedis.close();
    }

    @Test
    public void testJedisPool() throws Exception {
        //创建一个连接池对象
        //系统中应该是单例的。
        JedisPool jedisPool = new JedisPool("192.168.81.120", 6379);
        //从连接池中获得一个连接
        Jedis jedis = jedisPool.getResource();
        String result = jedis.get("test");
        System.out.println(result);
        //jedis必须关闭
        jedis.close();

        //系统关闭时关闭连接池
        jedisPool.close();

    }

    @Test
    public void testJedisCluster() throws Exception {
        //创建一个JedisCluster对象
        Set<HostAndPort> nodes = new HashSet<>();
        nodes.add(new HostAndPort("192.168.81.120", 7001));
        nodes.add(new HostAndPort("192.168.81.120", 7002));
        nodes.add(new HostAndPort("192.168.81.120", 7003));
        nodes.add(new HostAndPort("192.168.81.120", 7004));
        nodes.add(new HostAndPort("192.168.81.120", 7005));
        nodes.add(new HostAndPort("192.168.81.120", 7006));
        //在nodes中指定每个节点的地址
        //jedisCluster在系统中是单例的。
        JedisCluster jedisCluster = new JedisCluster(nodes);
        jedisCluster.set("name", "zhangsan");
        jedisCluster.set("value", "100");
        String name = jedisCluster.get("name");
        String value = jedisCluster.get("value");
        System.out.println(name);
        System.out.println(value);


        //系统关闭时关闭jedisCluster
        jedisCluster.close();
    }

}
