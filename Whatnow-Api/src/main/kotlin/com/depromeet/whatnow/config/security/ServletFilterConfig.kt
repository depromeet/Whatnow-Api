package com.depromeet.whatnow.config.security

import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.boot.web.servlet.FilterRegistrationBean
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Profile
import org.springframework.security.web.context.AbstractSecurityWebApplicationInitializer
import org.springframework.web.filter.ForwardedHeaderFilter
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer
import org.springframework.web.servlet.resource.ResourceUrlEncodingFilter
import javax.servlet.Filter

@Configuration
@Profile("prod", "staging", "dev")
class ServletFilterConfig(
    val forwardedHeaderFilter: ForwardedHeaderFilter,
    val httpContentCacheFilter: HttpContentCacheFilter,
) : WebMvcConfigurer {
    @Bean
    fun securityFilterChain(
        @Qualifier(AbstractSecurityWebApplicationInitializer.DEFAULT_FILTER_NAME)
        securityFilter: Filter?,
    ): FilterRegistrationBean<Filter> {
        val registration: FilterRegistrationBean<Filter> = FilterRegistrationBean(securityFilter)
        registration.order = Int.MAX_VALUE - 3
        registration.setName(AbstractSecurityWebApplicationInitializer.DEFAULT_FILTER_NAME)
        return registration
    }

    @Bean
    fun setResourceUrlEncodingFilter(): FilterRegistrationBean<ResourceUrlEncodingFilter> {
        val registrationBean: FilterRegistrationBean<ResourceUrlEncodingFilter> = FilterRegistrationBean(ResourceUrlEncodingFilter())
        registrationBean.order = Int.MAX_VALUE - 2
        return registrationBean
    }

    @Bean
    fun setForwardedHeaderFilterOrder(): FilterRegistrationBean<ForwardedHeaderFilter> {
        val registrationBean: FilterRegistrationBean<ForwardedHeaderFilter> = FilterRegistrationBean(forwardedHeaderFilter)
        registrationBean.order = Int.MAX_VALUE - 1
        return registrationBean
    }

    @Bean
    fun setHttpContentCacheFilterOrder(): FilterRegistrationBean<HttpContentCacheFilter> {
        val registrationBean: FilterRegistrationBean<HttpContentCacheFilter> = FilterRegistrationBean(httpContentCacheFilter)
        registrationBean.order = Int.MAX_VALUE
        return registrationBean
    }
}
