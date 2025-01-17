package com.vivekempire.TodoList.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@Component
public class RedisUtility {

    @Autowired
    private RedisTemplate redisTemplate;

    public void testRedis(){
        System.out.println(redisTemplate.getConnectionFactory().getConnection().ping());
    }

    public void set(String key,String value,long expiry){
        redisTemplate.opsForValue().set(key,value,expiry, TimeUnit.MINUTES);

    }

    public String get(String key){
        return (String) redisTemplate.opsForValue().get(key);
    }


}
