package com.depromeet.whatnow.config.redis

<<<<<<< HEAD
=======
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.jsontype.BasicPolymorphicTypeValidator
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
>>>>>>> develop
import org.springframework.cache.CacheManager
import org.springframework.cache.annotation.EnableCaching
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.redis.cache.RedisCacheConfiguration
import org.springframework.data.redis.cache.RedisCacheManager
import org.springframework.data.redis.connection.RedisConnectionFactory
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer
import org.springframework.data.redis.serializer.RedisSerializationContext
import org.springframework.data.redis.serializer.StringRedisSerializer
import java.time.Duration

@EnableCaching
@Configuration
class RedisCacheConfig {

<<<<<<< HEAD
=======
    val objectMapper: ObjectMapper = jacksonObjectMapper()
        .activateDefaultTyping(
            BasicPolymorphicTypeValidator.builder()
                .allowIfBaseType(Any::class.java)
                .build(),
            ObjectMapper.DefaultTyping.EVERYTHING,
        )

>>>>>>> develop
    @Bean
    fun oidcCacheManager(cf: RedisConnectionFactory): CacheManager {
        val redisCacheConfiguration = RedisCacheConfiguration.defaultCacheConfig()
            .serializeKeysWith(
                RedisSerializationContext.SerializationPair.fromSerializer(
                    StringRedisSerializer(),
                ),
            )
            .serializeValuesWith(
<<<<<<< HEAD
                RedisSerializationContext.SerializationPair.fromSerializer(
                    GenericJackson2JsonRedisSerializer(),
                ),
=======
                RedisSerializationContext.SerializationPair
                    .fromSerializer(
                        GenericJackson2JsonRedisSerializer(objectMapper),
                    ),
>>>>>>> develop
            )
            .entryTtl(Duration.ofDays(7L))
        return RedisCacheManager.RedisCacheManagerBuilder.fromConnectionFactory(cf)
            .cacheDefaults(redisCacheConfiguration)
            .build()
    }
}
