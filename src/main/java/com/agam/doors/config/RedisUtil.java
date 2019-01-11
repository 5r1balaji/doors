package com.agam.doors.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;

import java.util.concurrent.TimeUnit;

@Configuration
public class RedisUtil<T> {
    private RedisTemplate<String, T> redisTemplate;
    private ValueOperations<String,T> valueOperations;

    @Autowired
    RedisUtil(RedisTemplate<String,T> redisTemplate){
        this.redisTemplate = redisTemplate;
        this.valueOperations = redisTemplate.opsForValue();
    }

    public void putValue(String key,T value) {
        valueOperations.set(key, value);
    }
    public void putValueWithExpireTime(String key, T value, long timeout, TimeUnit unit) {
        valueOperations.set(key, value, timeout, unit);
    }
    public T getValue(String key) {
        return valueOperations.get(key);
    }
    public void setExpire(String key,long timeout,TimeUnit unit) {
        redisTemplate.expire(key, timeout, unit);
    }

    public void deleteValue(String key){
      redisTemplate.delete(key);
    }

}
