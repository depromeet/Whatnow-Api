package com.depromeet.whatnow.common.aop.verify

import com.depromeet.whatnow.domains.promiseuser.adaptor.PromiseUserAdaptor
import com.depromeet.whatnow.domains.promiseuser.exception.PromiseUserNotFoundException
import com.depromeet.whatnow.exception.custom.NotParticipatedInPromiseException
import com.depromeet.whatnow.exception.custom.PromiseIdConversionException
import com.depromeet.whatnow.exception.custom.PromiseIdParameterNotFoundException
import com.depromeet.whatnow.exception.custom.UserIdConversionException
import com.depromeet.whatnow.exception.custom.UserIdParameterNotFoundException
import org.aspectj.lang.ProceedingJoinPoint
import org.aspectj.lang.annotation.Around
import org.aspectj.lang.annotation.Aspect
import org.aspectj.lang.reflect.MethodSignature
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression
import org.springframework.stereotype.Component
import java.lang.NumberFormatException

@Aspect
@Component
@ConditionalOnExpression("\${ableCheckUserParticipation:true}")
class CheckUserParticipationAop(
    val promiseUserAdaptor: PromiseUserAdaptor,
) {
    @Around("@annotation(com.depromeet.whatnow.common.aop.verify.CheckUserParticipation)")
    fun verify(joinPoint: ProceedingJoinPoint): Any? {
        val signature = joinPoint.signature as MethodSignature
        val args = joinPoint.args
        val userId = findUserIdArg(signature.parameterNames, args)
        val promiseId = findPromiseIdArg(signature.parameterNames, args)

        if (userIdInPromise(userId, promiseId)) {
            return joinPoint.proceed()
        }
        throw NotParticipatedInPromiseException.EXCEPTION
    }

    private fun findUserIdArg(methodParameterNames: Array<String>, args: Array<Any>): Long {
        for (i in methodParameterNames.indices) {
            if ((methodParameterNames[i] == "userId")) {
                when (val arg = args[i]) {
                    is Long -> return arg
                    is String -> {
                        try {
                            return arg.toLong()
                        } catch (e: NumberFormatException) {
                            throw UserIdConversionException.EXCEPTION
                        }
                    }
                    else -> UserIdParameterNotFoundException.EXCEPTION
                }
            }
        }
        throw UserIdParameterNotFoundException.EXCEPTION
    }

    private fun findPromiseIdArg(methodParameterNames: Array<String>, args: Array<Any>): Long {
        for (i in methodParameterNames.indices) {
            if ((methodParameterNames[i] == "promiseId")) {
                when (val arg = args[i]) {
                    is Long -> return arg
                    is String -> {
                        try {
                            return arg.toLong()
                        } catch (e: NumberFormatException) {
                            throw PromiseIdConversionException.EXCEPTION
                        }
                    }
                    else -> PromiseIdParameterNotFoundException.EXCEPTION
                }
            }
        }
        throw PromiseIdParameterNotFoundException.EXCEPTION
    }

    private fun userIdInPromise(userId: Long, promiseId: Long): Boolean {
        return try {
            promiseUserAdaptor.findByPromiseIdAndUserId(promiseId, userId)
            true
        } catch (e: PromiseUserNotFoundException) {
            false
        }
    }
}
