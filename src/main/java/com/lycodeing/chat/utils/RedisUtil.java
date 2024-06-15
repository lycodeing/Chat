package com.lycodeing.chat.utils;

import io.lettuce.core.RedisException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

/**
 * @author xiaotianyu
 */
@Component
@Slf4j
public class RedisUtil<T> {

    public final RedisTemplate<String, T> redisTemplate;

    private final ValueOperations<String, T> valueOperations;

    public RedisUtil(RedisTemplate<String, T> redisTemplate) {
        this.redisTemplate = redisTemplate;
        this.valueOperations = redisTemplate.opsForValue();
        ;
    }

    /**
     * 设置值，并可选地设置过期时间
     *
     * @param key
     * @param value
     * @param expireTime 过期时间，单位为秒；如果为null，则不过期
     */
    public void set(String key, T value, Long expireTime) {
        try {
            valueOperations.set(key, value);
            if (expireTime != null) {
                redisTemplate.expire(key, expireTime, TimeUnit.SECONDS);
            }
        } catch (Exception e) {
            log.error("Failed to set key [{}] to Redis.", key, e);
            throw new RedisException(e.getMessage(), e);
        }
    }

    /**
     * 获取值
     *
     * @param key
     * @return
     */
    public T get(String key) {
        try {
            return valueOperations.get(key);
        } catch (Exception e) {
            log.error("Failed to get key [{}] from Redis.", key, e);
            // 根据实际情况，可以考虑返回null或抛出自定义异常
            throw new RedisException(e.getMessage(), e);
        }
    }

    /**
     * 删除值
     *
     * @param key
     */
    public void delete(String key) {
        try {
            redisTemplate.delete(key);
        } catch (Exception e) {
            log.error("Failed to delete key [{}] from Redis.", key, e);
            throw new RedisException(e.getMessage(), e);
        }
    }


}
