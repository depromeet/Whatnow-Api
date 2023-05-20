package com.depromeet.whatnow.common.aop.lock

import org.aspectj.lang.ProceedingJoinPoint
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Propagation
import org.springframework.transaction.annotation.Transactional

@Component
class RedissonCallNewTransaction {
    // 다른 트랜잭션이 걸린서비스가 해당 조인포인트(메서드를) 호출하더라도
    // 새로운 트랜잭션이 보장되어야합니다.
    // 락을 잡기전에 트랜잭션이 시작한건 무의미 하므로,

    // leaseTime 보다 트랜잭션 타임아웃을 작게 설정
    // leastTimeOut 발생전에 rollback 시키기 위함
    @Transactional(propagation = Propagation.REQUIRES_NEW, timeout = 9)
    fun proceed(joinPoint: ProceedingJoinPoint): Any? {
        return joinPoint.proceed()
    }
}
