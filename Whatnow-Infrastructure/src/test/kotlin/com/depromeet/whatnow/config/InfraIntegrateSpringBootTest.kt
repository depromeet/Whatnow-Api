package com.depromeet.whatnow.config

import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import kotlin.annotation.AnnotationRetention.RUNTIME
import kotlin.annotation.Retention

/** 도메인 모듈의 통합테스트의 편의성을 위해서 만든 어노테이션 -이찬진  */
@Target(
    AnnotationTarget.ANNOTATION_CLASS,
    AnnotationTarget.CLASS,
)
@Retention(RUNTIME)
@SpringBootTest(classes = [InfraIntegrateTestConfig::class])
@ActiveProfiles(resolver = InfraIntegrateProfileResolver::class)
@MustBeDocumented
annotation class InfraIntegrateSpringBootTest
