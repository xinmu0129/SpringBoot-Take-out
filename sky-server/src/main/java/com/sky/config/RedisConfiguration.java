package com.sky.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Configuration
@Slf4j
public class RedisConfiguration {

    @Bean
    public RedisTemplate redisTemplate(RedisConnectionFactory redisConnectionFactory){
        log.info("创建RedisTemplate对象...");
        RedisTemplate redisTemplate = new RedisTemplate();
        redisTemplate.setConnectionFactory(redisConnectionFactory);
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        return redisTemplate;
    }

    // --- 新增：加载 Lua 脚本的 Bean ---
    @Bean
    public DefaultRedisScript<Long> seckillScript() {
        DefaultRedisScript<Long> redisScript = new DefaultRedisScript<>();
        // 这里的路径对应 src/main/resources/seckill.lua
        redisScript.setLocation(new ClassPathResource("seckill.lua"));
        // 脚本返回值类型，对应 Lua 脚本里的 return 1, 0, -1
        redisScript.setResultType(Long.class);
        return redisScript;
    }
}