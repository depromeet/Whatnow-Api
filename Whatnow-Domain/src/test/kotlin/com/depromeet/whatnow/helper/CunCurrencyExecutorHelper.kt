package com.depromeet.whatnow.helper

import java.util.concurrent.CountDownLatch
import java.util.concurrent.Executors
import java.util.concurrent.atomic.AtomicLong

class CunCurrencyExecutorHelper {
    companion object {
        private const val numberOfThreads = 50
        private const val numberOfThreadPool = 10

        fun execute(operation: () -> Any?, successCount: AtomicLong) {
            val executorService = Executors.newFixedThreadPool(numberOfThreadPool)
            val latch = CountDownLatch(numberOfThreads)
            for (i in 1..numberOfThreads) {
                executorService.submit {
                    try {
                        operation()
                        // 오류없이 성공을 하면 성공횟수를 증가시킵니다.
                        successCount.getAndIncrement()
                    } catch (e: Throwable) {
                        // 에러뜨면 여기서 확인해보셔요!
                        println(e.javaClass.name)
                        println(e.stackTrace)
                    } finally {
                        latch.countDown()
                    }
                }
            }
            latch.await()
        }
    }
}
