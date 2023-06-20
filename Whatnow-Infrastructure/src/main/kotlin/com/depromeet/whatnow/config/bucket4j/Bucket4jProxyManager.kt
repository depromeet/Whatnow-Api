package com.depromeet.whatnow.config.bucket4j

import io.github.bucket4j.distributed.proxy.ProxyManager
import io.github.bucket4j.grid.jcache.JCacheProxyManager
import org.redisson.api.RedissonClient
import org.redisson.jcache.configuration.RedissonConfiguration
import org.springframework.cache.annotation.EnableCaching
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import javax.cache.CacheManager
import javax.cache.Caching

@Configuration
@EnableCaching
class Bucket4jProxyManager {
    @Bean
    fun cacheManager(redissonClient: RedissonClient?): CacheManager? {
        val manager = Caching.getCachingProvider().cacheManager
        val bucket4j = manager.getCache<Any, Any>("bucket4j")
        if (bucket4j == null) {
            manager.createCache("bucket4j", RedissonConfiguration.fromInstance<Any, Any>(redissonClient))
        }
        return manager
    }

    @Bean
    fun proxyManager(cacheManager: CacheManager): ProxyManager<String>? {
        val cache = cacheManager.getCache<Any, Any>("bucket4j")
        return cache?.let {
            JCacheProxyManager(cacheManager.getCache<String, ByteArray>("bucket4j"))
        }
    }
}
