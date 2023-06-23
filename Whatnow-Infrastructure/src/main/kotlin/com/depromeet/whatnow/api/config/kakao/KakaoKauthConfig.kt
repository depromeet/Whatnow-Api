package com.depromeet.whatnow.api.config.kakao

import feign.codec.Encoder
import feign.codec.ErrorDecoder
import feign.form.FormEncoder
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Import

@Import(KauthErrorDecoder::class)
class KakaoKauthConfig {
    @Bean
    @ConditionalOnMissingBean(value = [ErrorDecoder::class])
    fun commonFeignErrorDecoder(): KauthErrorDecoder {
        return KauthErrorDecoder()
    }

    @Bean
    fun formEncoder(): Encoder {
        return FormEncoder()
    }
}
