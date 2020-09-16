package com.fakebilibili.dao.daoimpl;

import com.fakebilibili.dao.RediasDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import redis.clients.jedis.JedisPool;

import java.lang.reflect.Field;
import java.util.Map;

@Repository
public class RedisDAOImpl implements RediasDAO {

    @Autowired
    private JedisPool jedisPool;

    @Override
    public String get(String key) {
        System.out.println("getFromRedis");
        return jedisPool.getResource().get(key);
    }

    @Override
    public String set(String key, String value) {
        System.out.println("put into redis"+key+"====="+value);
        return jedisPool.getResource().set(key, value);
    }

    @Override
    public String hget(String hkey, String key) {
        return jedisPool.getResource().hget(hkey,key);

    }

    @Override
    public Map<String, String> hgetAll(String key){
        Map<String, String> stringStringMap = jedisPool.getResource().hgetAll(key);
        //
        return stringStringMap;
    }

    @Override
    public <T> T mapToObject(Map<?, ?> map,Class<T> clazz) throws IllegalAccessException, InstantiationException {

        if (map == null){
            return null;
        }else{
            T t = clazz.newInstance();
            Field[] fields = clazz.getDeclaredFields();
            //fields.

        }



        return null;
    }

    @Override
    public <T> long hsetObject(T t,Class<T> clazz) {
        return 0;
    }

    @Override
    public long del(String key) {

        return jedisPool.getResource().del(key);
    }

    @Override
    public long hset(String hkey, String key, String value) {
        System.out.println("from jedis!!");
        return jedisPool.getResource().hset(hkey,key,value);
    }
}
