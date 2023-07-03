package com.depromeet.whatnow.api.config.ncp

import feign.codec.ErrorDecoder
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Import

@Import(NcpInfoErrorDecoder::class)
class NcpConfig {
    @Bean
    @ConditionalOnMissingBean(value = [ErrorDecoder::class])
    fun commonFeignErrorDecoder(): NcpInfoErrorDecoder {
        return NcpInfoErrorDecoder()
    }
}
