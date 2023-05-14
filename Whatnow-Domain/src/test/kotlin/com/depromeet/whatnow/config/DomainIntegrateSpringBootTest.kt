package com.depromeet.whatnow.config

import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
<<<<<<< HEAD
import java.lang.annotation.Documented
import java.lang.annotation.Retention
import java.lang.annotation.RetentionPolicy

/** 도메인 모듈의 통합테스트의 편의성을 위해서 만든 어노테이션 -이찬진  */
@Target(AnnotationTarget.ANNOTATION_CLASS, AnnotationTarget.CLASS)
@Retention(RetentionPolicy.RUNTIME)
@SpringBootTest(classes = [DomainIntegrateTestConfig::class])
@ActiveProfiles(resolver = DomainIntegrateProfileResolver::class)
@Documented
=======

/** 도메인 모듈의 통합테스트의 편의성을 위해서 만든 어노테이션 -이찬진  */
@Target(AnnotationTarget.ANNOTATION_CLASS, AnnotationTarget.CLASS)
@Retention(AnnotationRetention.RUNTIME)
@SpringBootTest(classes = [DomainIntegrateTestConfig::class])
@ActiveProfiles(resolver = DomainIntegrateProfileResolver::class)
@MustBeDocumented
>>>>>>> develop
annotation class DomainIntegrateSpringBootTest
