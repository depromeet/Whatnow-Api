package com.depromeet.whatnow.config

import com.depromeet.whatnow.WhatnowInfrastructureApplication
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.Configuration

@Configuration
@ComponentScan(basePackageClasses = [WhatnowInfrastructureApplication::class])
class InfraIntegrateTestConfig
