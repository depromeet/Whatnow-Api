package com.depromeet.whatnow.annotation

import org.springframework.core.annotation.AliasFor
import org.springframework.stereotype.Component
import kotlin.annotation.Retention

@Target(AnnotationTarget.ANNOTATION_CLASS, AnnotationTarget.CLASS)
@Retention(AnnotationRetention.RUNTIME)
@MustBeDocumented
@Component
annotation class Helper(@get:AliasFor(annotation = Component::class) val value: String = "")
