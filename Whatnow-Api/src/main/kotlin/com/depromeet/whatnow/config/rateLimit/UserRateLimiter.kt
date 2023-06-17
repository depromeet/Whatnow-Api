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
class UserRateLimiter(
    @Value("\${throttle.overdraft}")
    private val overdraft: Long,

    @Value("\${throttle.greedyRefill}")
    private val greedyRefill: Long,
) {
    private val buckets: ProxyManager<String>? = null

    // autowiring dependencies
    fun resolveBucket(key: String): Bucket {
        val configSupplier = configSupplierForUser
        return buckets!!.builder().build(key, configSupplier)
    }

    private val configSupplierForUser: Supplier<BucketConfiguration>
        private get() {
            val refill = Refill.greedy(greedyRefill, Duration.ofMinutes(1))
            val limit = Bandwidth.classic(overdraft, refill)
            return Supplier {
                BucketConfiguration.builder().addLimit(limit).build()
            }
        }
}
