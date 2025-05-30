package org.deslre.utils;

import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * ClassName: RedisUtil
 * Description: Redis工具类
 * Author: Deslrey
 * Date: 2025-05-30 16:59
 * Version: 1.0
 */
@Component
public class RedisUtil {

    @Resource
    private StringRedisTemplate stringRedisTemplate;

}
