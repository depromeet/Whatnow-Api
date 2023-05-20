package com.depromeet.whatnow.config

import com.depromeet.whatnow.common.aop.lock.RedissonLock
import com.depromeet.whatnow.helper.CunCurrencyExecutorHelper
import org.assertj.core.api.AssertionsForClassTypes.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.util.concurrent.atomic.AtomicLong

@DomainIntegrateSpringBootTest
class RedissonAopTests {

    @Autowired lateinit var orderService: OrderService

    @Service
    class OrderService(
        var stock: Int = 100,
    ) {

        @RedissonLock("id", "stockLock")
        fun decreaseStock(id: Int) {
            stock -= 1
        }
    }

    @Test
    fun `분산락 적용시 동시요청에 올바르게 재고가 감소해야한다`() {
        // given
        // when
        val successCount = AtomicLong()
        CunCurrencyExecutorHelper.execute(
            { orderService.decreaseStock(1) },
            successCount,
        )
        // then

        val remain = 100 - successCount.toInt()
        assertThat(orderService.stock).isEqualTo(remain)
    }
}
