package com.depromeet.whatnow.annotation

import com.depromeet.whatnow.interfaces.SwaggerExampleExceptions
import kotlin.reflect.KClass

@Target(
    AnnotationTarget.FUNCTION,
    AnnotationTarget.PROPERTY_GETTER,
    AnnotationTarget.PROPERTY_SETTER,
)
@Retention(AnnotationRetention.RUNTIME)
annotation class ApiErrorExceptionsExample(val value: KClass<out SwaggerExampleExceptions>)
