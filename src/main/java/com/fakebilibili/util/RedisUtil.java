package com.fakebilibili.util;

import redis.clients.jedis.Jedis;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * 操作redis缓存的工具类,简单的demo,方便入门初步理解redis
 *
 * @author Xuan
 *
 */
public final class RedisUtil {
    public RedisUtil() {
    }

    // 以下配置可用可不用
    private static Jedis jedisxuan;// redis实例
    private static String host;// 地址
    private static String port;// 端口
    private static String password;// 授权密码
    private static String timeout;// 超时时间：单位ms
    private static String maxIdle;// 最大空闲数：空闲链接数大于maxIdle时,将进行回收
    private static String maxActive;// 最大连接数：能够同时建立的"最大链接个数"
    private static String maxWait;// 最大等待时间：单位ms
    private static String testOnBorrow;// 在获取连接时，是否验证有效性

    // 静态代码块
    static {
        // 加载properties配置文件
        Properties properties = new Properties();
        InputStream is = RedisUtil.class.getClassLoader().getResourceAsStream(
                "redis.properties");
        try {
            properties.load(is);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        host = properties.getProperty("redis.host");
        port = properties.getProperty("redis.port");
        password = properties.getProperty("redis.password");
        timeout = properties.getProperty("redis.timeout");
        maxIdle = properties.getProperty("redis.maxIdle");
        maxActive = properties.getProperty("redis.maxActive");
        maxWait = properties.getProperty("redis.maxWait");
        testOnBorrow = properties.getProperty("redis.testOnBorrow");
        // 得到Jedis实例并且设置配置
        jedisxuan = new Jedis(host, Integer.parseInt(port),
                Integer.parseInt(timeout));
    }

    /**
     * 写入缓存
     *
     * @param key
     * @param value
     * @return
     */
    public static boolean set(final String key, String value) {
        boolean result = false;
        try {
            jedisxuan.set(key, value);
            result = true;
        } catch (Exception e) {
            System.out.println("set cache error");
        }
        return result;
    }

    /**
     * 读取缓存
     *
     * @param key
     * @return
     */
    public static Object get(final String key) {
        Object result = null;
        result = jedisxuan.get(key);
        return result;
    }

    /**
     * 删除key对应的value
     *
     * @param key
     */
    public static void remove(final String key) {
        if (key != null && key.length() >= 1 && !key.equals("")
                && jedisxuan.exists(key)) {
            jedisxuan.del(key);
        }
    }

    /**
     * 判断缓存中是否有key对应的value
     *
     * @param key
     * @return
     */
    public static boolean exists(final String key) {
        return jedisxuan.exists(key);
    }

    /**
     * 写入缓存(规定缓存时间)
     *
     * @param key
     * @param value
     * @param expireSecond
     * @return
     */
    public static boolean set(final String key, String value, Long expireSecond) {
        boolean result = false;
        try {
            // NX代表不存在才set,EX代表秒,NX代表毫秒
            jedisxuan.set(key, value, "NX", "EX", expireSecond);
            result = true;
        } catch (Exception e) {
            System.out.println("set cache error");
        }
        return result;
    }
}
