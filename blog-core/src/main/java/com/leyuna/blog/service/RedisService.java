package com.leyuna.blog.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

/**
 * @author LeYuna
 * @email 365627310@qq.com
 * @date 2022-10-18
 */
@Service
public class RedisService {

    @Autowired
    private RedisTemplate redisTemplate;

    public void setData(String key, Object o, TimeUnit timeUnit, Long time) {
        redisTemplate.opsForValue().set(key, o, time, timeUnit);
    }

    public void setData(String key, Object o) {
        redisTemplate.opsForValue().set(key, o);
    }

    public boolean hasKey(String key) {
        return redisTemplate.hasKey(key);
    }

    public Object getData(String key) {
        return redisTemplate.opsForValue().get(key);
    }
}
