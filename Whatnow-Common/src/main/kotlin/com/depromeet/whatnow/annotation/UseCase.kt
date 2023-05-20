package com.depromeet.whatnow.annotation

import org.springframework.core.annotation.AliasFor
import org.springframework.stereotype.Component
import java.lang.annotation.Documented
import java.lang.annotation.Retention
import java.lang.annotation.RetentionPolicy

@Target(AnnotationTarget.ANNOTATION_CLASS, AnnotationTarget.CLASS)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Component
annotation class UseCase(@AliasFor(annotation = Component::class) val value: String = "")
