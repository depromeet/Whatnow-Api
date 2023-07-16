package com.depromeet.whatnow.common.aop.verify

import com.depromeet.whatnow.domains.invitecode.exception.InviteCodeFormatMismatchException
import org.aspectj.lang.ProceedingJoinPoint
import org.aspectj.lang.annotation.Around
import org.aspectj.lang.annotation.Aspect
import org.springframework.stereotype.Component

@Aspect
@Component
class InviteCodeLengthValidationAspect {
    @Around("@annotation(com.depromeet.whatnow.common.aop.verify.InviteCodeLength)")
    fun validateInviteCodeFormat(joinPoint: ProceedingJoinPoint): Any? {
        val args = joinPoint.args
        val inviteCode = args[0]
        println("inviteCode: $inviteCode")
        if (inviteCode is String && inviteCode.length != 13) {
            throw InviteCodeFormatMismatchException.EXCEPTION
        }
        return joinPoint.proceed()
    }
}
