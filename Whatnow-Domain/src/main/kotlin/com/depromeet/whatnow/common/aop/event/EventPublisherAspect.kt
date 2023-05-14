package com.depromeet.whatnow.common.aop.event

import org.aspectj.lang.ProceedingJoinPoint
import org.aspectj.lang.annotation.Around
import org.aspectj.lang.annotation.Aspect
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression
import org.springframework.context.ApplicationEventPublisher
import org.springframework.context.ApplicationEventPublisherAware
import org.springframework.stereotype.Component

@Aspect
@Component
@ConditionalOnExpression("\${ableDomainEvent:true}")
class EventPublisherAspect : ApplicationEventPublisherAware {
    private lateinit var publisher: ApplicationEventPublisher

    private val appliedLocal: ThreadLocal<Boolean> = ThreadLocal.withInitial { false }

    @Around("@annotation(org.springframework.transaction.annotation.Transactional)")
    fun handleEvent(joinPoint: ProceedingJoinPoint): Any? {
        val appliedValue = appliedLocal.get()
        // nested를 쓰는이유
        // 트랜잭션 안에 트랜잭션이 또있는 경우대비
        // 트랜잭션 1. joinpoint.proceed ->
        // 트랜잭션 2. joinpoint.proceed ->
        // 트랜잭션 2. finally 정리 구문
        // 트랜잭션 1. finally 정리 구문
        // 트랜잭션 1. finally 정리 구문에서 최종적으로 정리를 하기 위함
        val nested: Boolean

        if (appliedValue) {
            nested = true
        } else {
            nested = false
            appliedLocal.set(true)
        }

        if (!nested) Events.setPublisher(publisher)

        return try {
            joinPoint.proceed()
        } finally {
            if (!nested) {
                Events.reset()
                appliedLocal.remove()
            }
        }
    }

    override fun setApplicationEventPublisher(eventPublisher: ApplicationEventPublisher) {
        publisher = eventPublisher
    }
}
