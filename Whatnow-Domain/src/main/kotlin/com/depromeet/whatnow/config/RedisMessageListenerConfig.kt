package com.depromeet.whatnow.config

import com.depromeet.whatnow.consts.REDIS_EXPIRE_EVENT_PATTERN
import com.depromeet.whatnow.domains.promiseactive.listener.RedisExpireEventRedisMessageListener
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.redis.connection.RedisConnectionFactory
import org.springframework.data.redis.listener.PatternTopic
import org.springframework.data.redis.listener.RedisMessageListenerContainer


@Configuration
class RedisMessageListenerConfig {
    @Bean
    fun redisMessageListenerContainer(
        redisConnectionFactory: RedisConnectionFactory,
        redisExpireEventRedisMessageListener: RedisExpireEventRedisMessageListener,
    ): RedisMessageListenerContainer {
        val redisMessageListenerContainer = RedisMessageListenerContainer()
        redisMessageListenerContainer.setConnectionFactory(redisConnectionFactory)
        redisMessageListenerContainer.addMessageListener(redisExpireEventRedisMessageListener, PatternTopic(REDIS_EXPIRE_EVENT_PATTERN))
        return redisMessageListenerContainer
    }
}
