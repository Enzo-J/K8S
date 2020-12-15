//package com.wenge.tbase.gateway.config;
//
//import java.time.Duration;
//
//import org.springframework.cache.CacheManager;
//import org.springframework.cache.annotation.CachingConfigurerSupport;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
//import com.fasterxml.jackson.annotation.JsonAutoDetect;
//import com.fasterxml.jackson.annotation.PropertyAccessor;
//import com.fasterxml.jackson.databind.ObjectMapper;
//
//@Configuration
//public class RedisConfig extends CachingConfigurerSupport {
//
//    @Bean
//    public CacheManager cacheManager(RedisConnectionFactory factory) {
//        //对象的序列化
//        RedisSerializationContext.SerializationPair valueSerializationPair
//                = RedisSerializationContext.SerializationPair.fromSerializer(jackson2JsonRedisSerializer());
//        //全局redis缓存过期时间
//        RedisCacheConfiguration redisCacheConfiguration = RedisCacheConfiguration.defaultCacheConfig()
//                .entryTtl(Duration.ofDays(1))
////                .serializeKeysWith()
//                .serializeValuesWith(valueSerializationPair);
//
//        return new RedisCacheManager(RedisCacheWriter.nonLockingRedisCacheWriter(factory), redisCacheConfiguration);
//    }
//
//    private Jackson2JsonRedisSerializer<Object> jackson2JsonRedisSerializer() {
//
//        Jackson2JsonRedisSerializer<Object> jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer<>(Object.class);
//        jackson2JsonRedisSerializer.setObjectMapper(objectMapper());
//        return jackson2JsonRedisSerializer;
//    }
//
//    private ObjectMapper objectMapper() {
//        ObjectMapper objectMapper = new ObjectMapper();
//        objectMapper.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
//        objectMapper.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
//        return objectMapper;
//    }
//
//
//}