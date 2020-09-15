package com.fakebilibili;

import org.junit.Test;
import redis.clients.jedis.Jedis;

public class RedisTest {

    /**
     * 测试Redis连接
     */
    @Test
    public void testRedisCon(){
        Jedis jedis = new Jedis("localhost");
        jedis.auth("renlong");
        jedis.set("renlong","这个是redis测试用的");
        jedis.lpush("1","dfsd");
        System.out.println(jedis.keys("*"));
        System.out.println(jedis.ping());
    }
}
