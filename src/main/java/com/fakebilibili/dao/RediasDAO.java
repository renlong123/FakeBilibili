package com.fakebilibili.dao;

import java.util.Map;

public interface RediasDAO {

    public String get(String key);
    public String set(String key,String value);
    public String hget(String hkey,String key);
    public long hset(String hkey,String key,String value);
    public Map<String, String> hgetAll(String key);
    public <T> T mapToObject(Map<?,?> map,Class<T> clazz) throws IllegalAccessException, InstantiationException;
    public <T> long hsetObject(T t,Class<T> clazz);
    public long del(String key);
}
