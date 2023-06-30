package com.depromeet.whatnow.config.rateLimit

import io.github.bucket4j.Bandwidth
import io.github.bucket4j.Bucket
import io.github.bucket4j.BucketConfiguration
import io.github.bucket4j.Refill
import io.github.bucket4j.distributed.proxy.ProxyManager
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import java.time.Duration
import java.util.function.Supplier

@Component
class IPRateLimiter(
    // autowiring dependencies
    val buckets: ProxyManager<String>? = null,

    @Value("\${throttle.overdraft}")
    val overdraft: Long = 0,

    @Value("\${throttle.greedyRefill}")
    private val greedyRefill: Long = 0,
) {
    fun resolveBucket(key: String?): Bucket {
        val configSupplier: Supplier<BucketConfiguration> = configSupplierForUser
        return buckets!!.builder().build(key, configSupplier)
    }

    private val configSupplierForUser: Supplier<BucketConfiguration>
        get() {
            val refill: Refill = Refill.greedy(greedyRefill, Duration.ofMinutes(1))
            val limit: Bandwidth = Bandwidth.classic(overdraft, refill)
            return Supplier {
                BucketConfiguration.builder().addLimit(limit).build()
            }
        }
}
