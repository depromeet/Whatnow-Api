package com.depromeet.whatnow.api.promise.annotation

import com.depromeet.whatnow.config.security.SecurityUtils
import com.depromeet.whatnow.domains.promise.adaptor.PromiseAdaptor
import com.depromeet.whatnow.domains.promise.exception.NotHostException
import org.aspectj.lang.ProceedingJoinPoint
import org.aspectj.lang.annotation.Around
import org.aspectj.lang.annotation.Aspect
import org.springframework.stereotype.Component

@Aspect
@Component
class RequiresMainUserAspect(
    val promiseAdaptor: PromiseAdaptor,
) {
    @Around("@annotation(requiresMainUser)")
    fun validateMainUserAccess(joinPoint: ProceedingJoinPoint, requiresMainUser: RequiresMainUser): Any? {
        val userId = SecurityUtils.currentUserId
        val promiseId = joinPoint.args[0] as Long
        val promise = promiseAdaptor.queryPromise(promiseId)

        if (userId == promise.mainUserId) {
            return joinPoint.proceed()
        } else {
            throw NotHostException()
        }
    }
}
