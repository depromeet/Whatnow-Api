package com.depromeet.whatnow.common.aop.verify

import com.depromeet.whatnow.domains.promise.adaptor.PromiseAdaptor
import com.depromeet.whatnow.domains.promise.exception.PromiseNotActivateException
import com.depromeet.whatnow.exception.custom.PromiseIdConversionException
import com.depromeet.whatnow.exception.custom.PromiseIdParameterNotFoundException
import org.aspectj.lang.ProceedingJoinPoint
import org.aspectj.lang.annotation.Around
import org.aspectj.lang.annotation.Aspect
import org.aspectj.lang.reflect.MethodSignature
import org.springframework.stereotype.Component
import java.lang.NumberFormatException
import java.time.LocalDateTime

@Aspect
@Component
class ActivePromiseAop(
    val promiseAdaptor: PromiseAdaptor,
) {
    @Around("@annotation(activePromise)")
    fun validateActivePromise(joinPoint: ProceedingJoinPoint, activePromise: ActivePromise): Any? {
        val args = joinPoint.args
        val signature = joinPoint.signature as MethodSignature
        val now = LocalDateTime.now()
        val promiseId = findPromiseIdArg(signature.parameterNames, args)

        val promise = promiseAdaptor.queryPromise(promiseId)
        if (promise.isActive(now)) {
            return joinPoint.proceed()
        } else {
            throw PromiseNotActivateException()
        }
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
}
