package com.example.jediscache;

import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

@Component
public class RedisConfig {

    @Bean
    public Jedis createdJedisPool(){
        var jedisPool =  new JedisPool("127.0.0.1", 6379);
        Jedis jedis = jedisPool.getResource();
        return jedis;
    }
}
