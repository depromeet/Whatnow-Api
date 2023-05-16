package com.depromeet.whatnow.common.aop.lock

import com.depromeet.whatnow.exception.custom.BadLockIdentifierException
import com.depromeet.whatnow.exception.custom.NotAvailableRedissonLockException
import org.aspectj.lang.ProceedingJoinPoint
import org.aspectj.lang.annotation.Around
import org.aspectj.lang.annotation.Aspect
import org.aspectj.lang.reflect.MethodSignature
import org.redisson.api.RedissonClient
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression
import org.springframework.stereotype.Component
import org.springframework.transaction.TransactionTimedOutException

@Aspect
@Component
@ConditionalOnExpression("\${ableRedissonLock:true}")
class RedissonLockAop(
    private val redissonClient: RedissonClient,
    private val redissonCallNewTransaction: RedissonCallNewTransaction,
) {

    @Around("@annotation(com.depromeet.whatnow.common.aop.lock.RedissonLock)")
    fun lock(joinPoint: ProceedingJoinPoint): Any? {
        val signature = joinPoint.signature as MethodSignature
        val method = signature.method
        val redissonLock = method.getAnnotation(RedissonLock::class.java)
        val baseKey: String = redissonLock.lockName
        val dynamicKey = getDynamicKeyFromMethodArg(signature.parameterNames, joinPoint.args, redissonLock.identifier)

        val rLock = redissonClient.getLock("$baseKey:$dynamicKey")

        try {
            val available = rLock.tryLock(redissonLock.waitTime, redissonLock.leaseTime, redissonLock.timeUnit)
            if (!available) {
                throw NotAvailableRedissonLockException.EXCEPTION
            }

            return redissonCallNewTransaction
                .proceed(joinPoint)
        } catch (e: TransactionTimedOutException) { // WhatnowCodeException , WhatnowDynamicException
            throw e
        } finally {
            try {
                rLock.unlock()
            } catch (e: IllegalMonitorStateException) {
//                log.error(e.toString() + baseKey + dynamicKey)
                throw e
            }
        }
    }

    fun getDynamicKeyFromMethodArg(
        methodParameterNames: Array<String>,
        args: Array<Any>,
        paramName: String,
    ): String {
        for (i in methodParameterNames.indices) {
            if ((methodParameterNames[i] == paramName)) {
                return args[i].toString()
            }
        }
        throw BadLockIdentifierException.EXCEPTION
    }
}
