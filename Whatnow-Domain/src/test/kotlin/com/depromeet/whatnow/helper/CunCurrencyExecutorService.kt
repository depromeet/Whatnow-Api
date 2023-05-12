package com.depromeet.whatnow.helper

import java.util.concurrent.CountDownLatch
import java.util.concurrent.Executors
import java.util.concurrent.atomic.AtomicLong

class CunCurrencyExecutorService {
    companion object {
        private const val numberOfThreads = 10
        private const val numberOfThreadPool = 5

        fun execute(operation: () -> Any?, successCount: AtomicLong) {
            val executorService = Executors.newFixedThreadPool(numberOfThreadPool)
            val latch = CountDownLatch(numberOfThreads)
            for (i in 1..numberOfThreads) {
                executorService.submit {
                    try {
                        operation();
                        // 오류없이 성공을 하면 성공횟수를 증가시킵니다.
                        successCount.getAndIncrement();
                    } catch (e: Throwable) {
                        // 에러뜨면 여기서 확인해보셔요!
//                    log.info(e.javaClass.name)
                    } finally {
                        latch.countDown()
                    }
                }
            }
            latch.await()
        }
    }
}
