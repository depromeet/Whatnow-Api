package com.depromeet.whatnow.annotation

import org.springframework.stereotype.Component

@Target(
    AnnotationTarget.FUNCTION,
    AnnotationTarget.PROPERTY_GETTER,
    AnnotationTarget.PROPERTY_SETTER,
    AnnotationTarget.PROPERTY,
)
@Retention(AnnotationRetention.RUNTIME)
@MustBeDocumented
@Component
annotation class ExplainError(val value: String = "")
